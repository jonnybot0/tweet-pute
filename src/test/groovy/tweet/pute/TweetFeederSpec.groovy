package tweet.pute

import spock.lang.Specification
import grails.test.mixin.Mock

/**
 * Created by jonny on 5/16/15.
 */
@Mock([Url, Pic, Tweet, Emoji, Hashtag])
class TweetFeederSpec extends Specification {
    TweetFeeder tweetFeeder = new TweetFeeder()

    def setup() {
    }

    def cleanup() {
    }

    void "test make collaborators"() {
        setup:
            def tweet = new Tweet(text: 'Yo dawgs',
                tweetId: 1L,
                user: 'jonnytron',
                twitterDate: new Date())
            List objects = tweetFeeder.makeCollaborators(domain, data, tweet)
        expect: objects.each{
                assert it.instanceOf(domain)
            }
        where:
            domain      | data
            Url         | ['http://google.com']
            Pic         | ['http://pic.twitter.com/stuff']
            Emoji       | ['\u1F601']
            Hashtag     | ['theStruggleIsReal']
    }
}
