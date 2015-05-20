package tweet.pute

import grails.test.mixin.*
import spock.lang.*
import tweet.pute.TweetController

@TestFor(TweetController)
@Mock([Tweet, TweetPuteService])
class TweetControllerSpec extends Specification {
    Random random = new Random()
    static Date then = new Date()-1
    static Date soon = new Date()+1

    def setup() {
        Tweet.metaClass.getDateCreated = {twitterDate}
        //Create some tweets
        def oldTweet = new Tweet(text: "A later tweet",
                tweetId: 1L,
                user: '123',
                twitterDate: then)
        oldTweet.save()
        [2, 3, 4, 5, 6].each{
            def tweet = new Tweet(text: "Sample tweet $it",
                tweetId: it.toLong(),
                user: it.toString(),
                twitterDate: new Date() )
            tweet.save()
        }
        def newTweet = new Tweet(text: "A later tweet",
                tweetId: 54L,
                user: '123',
                twitterDate: soon )
        newTweet.save()
    }

    def cleanup() {
        Tweet.list().each{it.delete(flush: true)}
    }

    def populateValidParams(params) {
        assert params != null
        params["text"] = 'OH HAI GUYS'
        params["twitterId"] = random.nextInt() % 1000
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()
            println model
        then:"The model is correct"
            model.tweetList
            model.tweetCount == 7
            model.average.perHour == 7/48
            model.average.perMinute == 7/(24*2*60)
            model.average.perSecond == 7/(24*2*60*60)

    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def tweet = new Tweet(params)
            controller.show(tweet)

        then:"A model is populated containing the domain instance"
            model.tweet == tweet
    }
}
