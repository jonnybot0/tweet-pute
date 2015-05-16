@Grab(group='org.codehaus.gpars', module='gpars', version='1.2.1')
@Grab(group="org.twitter4j",      module="twitter4j-core", version="4.0.3")
@Grab(group="org.twitter4j",      module="twitter4j-stream", version="4.0.3")
import groovyx.gpars.*
import twitter4j.*

def stream = new TwitterStreamFactory().getInstance()

def listener = new StatusListener() {
    @Override
    void onStatus(Status status) {
        println status.getUser().getName() + " @ " + status.getCreatedAt() + " : " + status.getText()
    }

    @Override
    void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { }

    @Override
    void onTrackLimitationNotice(int i) { }

    @Override
    void onScrubGeo(long l, long l2) { }

    @Override
    void onStallWarning(StallWarning stallWarning) { }
 
    @Override
    void onException(Exception e) {
        e.printStackTrace()
    }
}
stream.addListener(listener)
stream.sample() //This is what calls the sample API endpoint, instead of the main API