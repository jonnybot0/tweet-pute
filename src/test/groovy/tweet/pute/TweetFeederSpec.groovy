package tweet.pute

import spock.lang.Specification
import spock.lang.Unroll
import grails.test.mixin.Mock

/**
 * Created by jonny on 5/16/15.
 */
@Mock([Url, Pic, Tweet, Emoji, Hashtag])
class TweetFeederSpec extends Specification {
    TweetFeeder tweetFeeder = new TweetFeeder()
    static List<String> someEmojis =  [[-16, -97, -108, -118], [-16, -97, -104, -76],
            [-16, -97, -111, -106], [-16, -97, -115, -112], [-16, -97, -102, -109],
            [-16, -97, -102, -113], [-16, -97, -102, -103], [-16, -97, -108, -102],
            [-16, -97, -109, -95], [-16, -97, -112, -117], [-16, -97, -114, -112],
            [-16, -97, -108, -126], [-16, -97, -114, -85], [-30, -104, -108],
            [-16, -97, -115, -102]].collect{new String(it as byte[], 'UTF-8')}

    def setup() {
    }

    def cleanup() {
    }

    @Unroll("Testing make collaborators for #domain.simpleName")
    void "test make collaborators"() {
        setup:
            def tweet = new Tweet(text: "Yo dawgs $data",
                tweetId: 1L,
                user: 'jonnytron',
                twitterDate: new Date())
            List objects = tweetFeeder.makeCollaborators(domain, data, tweet)
            String objectSetPropertyName = "${domain.simpleName}".toLowerCase() + 's'
            println "Created these objects: $objects"
        expect: "All objects are created"
            objects.size() == data.size()
            objects.each{
                assert it.instanceOf(domain)
                assert it.tweets == [tweet] as Set
                tweet."$objectSetPropertyName".contains(it)
            }
        where:
            domain      | data
            Url         | ['http://google.com']
            Url         | ['https://www.missouristate.edu', 'http://www.realultimatepower.net/', 'http://winnski.com/bs/', 'http://www.angelfire.com/']
            Pic         | ['http://pic.twitter.com/stuff']
            Emoji       | someEmojis
            Hashtag     | ['theStruggleIsReal', 'firstWorldProblems', 'yoDawg']
    }

    void "test emoji construction"() {
        setup:
            println "Looking for ${someEmojis.size()} emojis"
            def tweet = new Tweet(text: "Yo dawgs ${someEmojis.join(' ')}",
                tweetId: 42L,
                user: 'jonnytron',
                twitterDate: new Date())
            tweetFeeder.makeEmojis(tweet)
            println tweet.text
            def madeEmojis = Emoji.list()
        expect:
            madeEmojis.size() == someEmojis.size()
            madeEmojis*.text.contains(someEmojis[0])
    }
}
