package tweetpute
import twitter4j.*
class TweetFeeder implements StatusListener {
    void onStatus(Status status) {
        println status.getUser().getName() + " @ " + status.getCreatedAt() + " : " + status.getText()
    }

    void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { }

    void onTrackLimitationNotice(int i) { }

    void onScrubGeo(long l, long l2) { }

    void onStallWarning(StallWarning stallWarning) { }

    void onException(Exception e) {
        e.printStackTrace()
    }
}