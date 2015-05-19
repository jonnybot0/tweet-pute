package tweet.pute
import twitter4j.*
import groovyx.gpars.GParsPool
import java.net.MalformedURLException
import com.vdurmont.emoji.EmojiManager

class TweetFeeder implements StatusListener {

    def em = new EmojiManager()
    List allEmojis = em.getAll().collect{it.getUnicode()}

    void onStatus(Status status) {
        GParsPool.withPool {
            Tweet.withNewSession {
                def tweet = makeTweet(status)
                if (tweet) {
                    makeHashtags(status, tweet)
                    makeEmojis(tweet)
                    makeUrls(status, tweet)
                }
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
                user: status.getUser()?.getName(),
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
        List<String> hashtags = status.getHashtagEntities()?.collect { it.getText() }
        makeCollaborators(Hashtag, hashtags, tweet)
    }

    def makeEmojis(Tweet tweet) {
        List<String> emojis = allEmojis.findAll{
            tweet.text.contains(it)
        }
        if (emojis) {
            return makeCollaborators(Emoji, emojis, tweet)
        }
        else {return []}
    }

    def makeUrls(Status status, Tweet tweet) {
        List<String> urls = status.getURLEntities()*.getExpandedURL()
        List<String> picUrls = status.getMediaEntities()*.getExpandedURL()
        List<String> otherPics = urls.findAll{it.find(~/instagr[.]?am/)}
        picUrls = (picUrls + otherPics).unique()
        urls = urls - picUrls
        makeCollaborators(Url, urls, tweet) + makeCollaborators(Pic, picUrls, tweet)
    }

    List makeCollaborators(Class domainClass, List listOfTextData, Tweet tweet) {
        String domainName = domainClass.simpleName
        String addMethod = "addTo$domainName" + 's'
        List objects = []
        domainClass.withNewSession {
            objects = listOfTextData.findResults{ text ->
                def domainInstance = domainClass.findByText(text) ?: domainClass.newInstance(text: text)
                domainInstance.addToTweets(tweet)
                if (!domainInstance.save()) {
                    println "Failed to save $domainName with data $text"
                    domainInstance.errors.allErrors.each{println it}
                    return null
                }
                else {
                    //println "Created domain instance $domainInstance"
                    tweet."$addMethod"(domainInstance)
                    return domainInstance
                }
            }
            tweet.save(flush: true)
        }
        return objects
    }
}