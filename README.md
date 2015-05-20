This app consumes the Twitter Streaming API's sample endpoint and provides some statistics about the tweets and their contents (Emojis, URLs, and pictures).

#Setup
Clone the repository.

##Install the JDK
If you haven't already, download [the JDK from Oracle and install it](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). Setup an environment variable called JAVA_HOME to point to the JDK directory, and add the \bin directory within JAVA_HOME to your path.

I developed this using Java 8, but Java 7 should work as well.

## Install Grails 3.0.1
Detailed instructions, including videos on installing Grails are available at https://grails.github.io/grails-doc/3.0.1/guide/gettingStarted.html#requirements.

###The Short Version
If you're on a Unix-like system (Mac OSX, Linux, FreeBSD, Solaris, even Cygwin- basically any system with bash), then the [gvm (Groovy enVironment Manager) tool](http://gvmtool.net/) is the easiest way to download and install Grails. Install gvmtool with their one-line bash command (`curl -s get.gvmtool.net | bash` as of this writing). Then run `gvm install grails 3.0.1` to download the needed version of Grails and automatically have it added to your path. Nifty, right? Note that gvm's installer will modify your .bashrc and .bash_profile files, so if you've got a fairly customized setup there, you may need to do some hacking to make it work right.

If you're on Windows, you have a bit more work to do. Download [Grails 3.0.1](https://github.com/grails/grails-core/releases/download/v3.0.1/grails-3.0.1.zip) and unzip it somewhere. Create an environment variable called GRAILS_HOME that points to the unzipped folder (probably called grails-3.0.1), and add %GRAILS_HOME%/bin to your PATH environment variable.

##Configure Twitter4j
This app uses the [Twitter4j](http://twitter4j.org) library to access the Twitter streaming API. Add a twitter4j.properties file to the root directory, and enter the following.

```
debug=true
oauth.consumerKey=*********************
oauth.consumerSecret=******************************************
oauth.accessToken=**************************************************
oauth.accessTokenSecret=******************************************
```

Replace the asterisks with their appropriate values using your Twitter account. You can set them up at https://apps.twitter.com/. Create a new app and then access its Keys and Access Tokens tab to generate the consumer key, consumer secret, access token, and access token secret. Notably, this app will not post to Twitter on your behalf, but Twitter4j will error unless supplied with all four credentials.

You can also set these same properties using other means, [as described in the Twitter4j documentation](http://twitter4j.org/en/configuration.html).

#Running the application
Once Grails is installed and working (`grails -version` is a good quick-check), and Twitter4j setup for Oauth, cd into the directory and `grails run-app`.

tweet-pute will process tweets in the background. You can view the results at the locally running web server; by default, this will be at http://localhost:8080/.

#Available Statistics
* Tweet statistics in JSON format: http://localhost:8080/tweet/stats
    * Total number of tweets received
    * Average tweets per hour/minute/second
    * Percent of tweets that contains emojis
    * Percent of tweets that contain a url
    * Percent of tweets that contain a photo url (pic.twitter.com or instagram)
* Top hashtags - Visit the Hashtag Controller and [sort by the tweets in descending order](http://localhost:8080/hashtag/index?sort=tweets&max=10&order=desc). Also in [JSON](http://localhost:8080/hashtag/index.json?sort=tweets&max=10&order=desc) and [XML](http://localhost:8080/hashtag/index.xml?sort=tweets&max=10&order=desc)
* Top emojis in tweets - Visit the Emoji Controller and [sort by the tweets in descending order](http://localhost:8080/emoji/index?sort=tweets&max=10&order=desc). Also in [JSON](http://localhost:8080/emoji/index.json?sort=tweets&max=10&order=desc) and [XML](http://localhost:8080/emoji/index.xml?sort=tweets&max=10&order=desc)
* Top domains of urls in tweets - Top 7 on the [url Controller's index page](http://localhost:8080/url/index); orderd JSON list at [stats page](http://localhost:8080/url/stats). You can also [sort the Url index page by Tweets](http://localhost:8080/url/index?sort=tweets&max=10&order=desc) to see how often a particular url gets shared.
