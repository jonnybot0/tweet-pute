import twitter4j.TwitterStreamFactory
import tweetpute.TweetFeeder
import groovyx.gpars.GParsPool

class BootStrap {

    def init = { servletContext ->
        GParsPool.withPool {
            def stream = new TwitterStreamFactory().getInstance()
            def listener = new TweetFeeder()
            stream.addListener(listener)
            stream.sample()
        }
    }
    def destroy = {
    }
}
