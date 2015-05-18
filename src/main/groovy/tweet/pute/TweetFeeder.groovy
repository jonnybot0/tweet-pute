package tweet.pute
import twitter4j.*
import com.vdurmont.emoji.*

class TweetFeeder implements StatusListener {

    static allEmojis = EmojiManager.getAll()

    void onStatus(Status status) {
        GPars.
        Tweet.withNewSession {
            def tweet = makeTweet(status)
            if (tweet) {
                //TODO: Make these operations parallel? -- may not be able to due to hibernate
                makeHashtags(status, tweet)
                makeEmojis(tweet)
                makeUrls(tweet)
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

    def makeHashtags(Status status, Tweet tweet) {
        List<String> hashtags = status.getHashTagEntities()?.collect { it.getText() }
        makeCollaborators(Hashtag, hashtags, tweet)
    }

    def makeEmojis(Tweet tweet) {
        List<String> emojis = tweet.text.toArray().collect{}
        makeCollaborators(Emoji, emojis, tweet)
    }

    def makeUrls(Tweet tweet) {
        List<String> urls = tweet.text.collect{} //Find some way to collect the URLs
        List<String> picUrls = urls.findAll{it.matches(/(pic\.twitter\.com)||instagram/)} //Distinguish pics
        urls = urls - picUrls
        makeCollaborators(Url, urls) + makeCollaborators(Pic, picUrls)
    }

    List makeCollaborators(Class domainClass, List listOfTextData, Tweet tweet) {
        String domainName = domainClass.simpleName
        String addMethod = "addTo$domainName" + 's'
        List objects = []
        domainClass.withNewSession {
            objects = listOfTextData.collect{ text ->
                def domainInstance = domainClass.findByText(text) ?: domainClass.newInstance(text: text)
                tweet."$addMethod"(domainInstance)
                if (!domainInstance.save()) {
                    println "Failed to save $domainName with data $text"
                }
            }
            tweet.save()
        }
        return objects
    }
}