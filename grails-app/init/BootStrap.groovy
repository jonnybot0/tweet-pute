import twitter4j.TwitterStreamFactory
import tweet.pute.TweetFeeder
import groovyx.gpars.GParsPool

class BootStrap {

    def init = { servletContext ->
        /*
        GParsPool.withPool {
            def stream = new TwitterStreamFactory().getInstance()
            def listener = new TweetFeeder()
            stream.addListener(listener)
            stream.sample()
        }
        */
    }
    def destroy = {
    }
}
