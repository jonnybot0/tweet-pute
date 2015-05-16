package tweetpute
import twitter4j.*
class TweetFeeder implements StatusListener {
    void onStatus(Status status) {
        println status.getUser().getName() + " @ " + status.getCreatedAt() + " : " + status.getText()
        Tweet.withNewSession {
            def tweet = new Tweet(text: status.getText(),
                    tweetId: status.getId(),
                    user: status.getUser().getName(),
                    twitterDate: status.getCreatedAt()
            )
            if (!tweet.save()) {
                println "Failed to save tweet"
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
}