import twitter4j.TwitterStreamFactory
import tweetpute.TweetFeeder
class BootStrap {

    def init = { servletContext ->
        def stream = new TwitterStreamFactory().getInstance()
        def listener = new TweetFeeder()
        stream.addListener(listener)
        stream.sample()
    }
    def destroy = {
    }
}
