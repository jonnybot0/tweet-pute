package tweet.pute
import twitter4j.*
import grails.core.GrailsClass
class TweetFeeder implements StatusListener {
    void onStatus(Status status) {
        Tweet.withNewSession {
            def tweet = makeTweet(status)
            if (tweet) {
            }
        }
    }

    void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { }

    void onTrackLimitationNotice(int i) { }

    void onScrubGeo(long l, long l2) { }

    void onStallWarning(StallWarning stallWarning) { }

    void onException(Exception e) {
        e.printStackTrace()
    }

    def makeTweet(Status status) {
        def tweet = new Tweet(text: status.getText(),
                tweetId: status.getId(),
                user: status.getUser().getName(),
                twitterDate: status.getCreatedAt()
        )
        if (!tweet.save()) {
            println "Failed to save tweet"
            tweet.errors.allErrors.each{println it}
            return false
        }
        else {
            return tweet
        }
    }

    def makeHashtags(status, tweet) {
        List<String> hashtags = status.getHashTagEntities()?.collect { it.getText() }
        hashtags.eachParallel { hashtagText ->
            Hashtag.withNewSession {
                def hashtag = Hashtag.findByText(hashtagText) ?: new Hashtag(text: hashtagText)
                tweet.addToHashtags(hashtag)
                tweet.save()
            }
        }
    }

    def makeEmojis(tweet) {
        def emojiRegex = ~/ /
        List<String> emojis = tweet.text =~ emojiRegex
        emojis.each { emojiText ->
            Emoji.withNewSession {
                def emoji = Emoji.findByText(emojiText) ?: new Emoji(emoji: emojiText)
                tweet.addToEmojis(emoji)
                tweet.save()
            }
        }
    }

    def makeUrls(tweet) {
        List<String> urls = tweet.text.collect{} //Find some way to collect the URLs
        List<String> picUrls = urls.findAll{it.matches(/(pic\.twitter\.com)||instagram/)} //Distinguish pics
        urls = urls - picUrls
        makeCollaborators(Url, urls)
        makeCollaborators(Pic, picUrls)
    }

    List makeCollaborators(Class domainClass, List listOfTextData, Tweet tweet) {
        String domainName = domainClass.simpleName
        String addMethod = "addTo$domainName" + 's'
        List objects = []
        domainClass.withNewSession {
            objects = listOfTextData.collect{ text ->
                def domainInstance = domainClass.findByText(text) ?: domainClass.newInstance(text: text)
                tweet."$addMethod"(domainInstance)
                domainInstance
            }
            tweet.save()
        }
        return objects
    }
}