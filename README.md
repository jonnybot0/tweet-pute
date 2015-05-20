This app consumes the Twitter Streaming API's sample endpoint and provides some statistics about the tweets and their contents (Emojis, URLs, and pictures).

#Dependencies
This app was built using Grails 3.0.1 and tested under Java 8 updates 40 and 45 and Java 7. 

#Setup
##Install the JDK
If you haven't already, download the JDK from Oracle and install it. Setup an environment variable called JAVA_HOME to point to the JDK directory, and add the \bin directory within JAVA_HOME to your path.

## Install Grails 3.0.1
If you're on a Unix-like system (Mac OSX, Linux, FreeBSD, Solaris, even Cygwin- basically any system with bash), then the [gvm (Groovy enVironment Manager) tool](http://gvmtool.net/) is the easiest way to download and install Grails. Install gvmtool with their one-line bash command (`curl -s get.gvmtool.net | bash` as of this writing). Then run `gvm install grails 3.0.1` to download the needed version of Grails and automatically have it added to your path. Nifty, right? Note that gvm's installer will modify your .bashrc and .bash_profile files, so if you've got a fairly customized setup there, you may need to do some hacking to make it work right.

If you're on Windows, you have a bit more work to do. Manually download [Grails 3.0.1](https://github.com/grails/grails-core/releases/download/v3.0.1/grails-3.0.1.zip) and unzip it somewhere. Create an environment variable called GRAILS_HOME that points to the unzipped folder (probably called grails-3.0.1), and add %GRAILS_HOME%/bin to your PATH environment variable.

#Available Statistics
* Tweet statistics http://localhost:8080/tweet/stats
** Total number of tweets received
** Average tweets per hour/minute/second
** Percent of tweets that contains emojis
** Percent of tweets that contain a url
** Percent of tweets that contain a photo url (pic.twitter.com or instagram)
* Top hashtags - Visit the Hashtag Controller and [sort by the tweets in descending order](http://localhost:8080/hashtag/index?sort=tweets&max=10&order=desc). Also in [JSON](http://localhost:8080/hashtag.json/index?sort=tweets&max=10&order=desc) and [XML](http://localhost:8080/hashtag.xml/index?sort=tweets&max=10&order=desc)
* Top emojis in tweets - Visit the Emoji Controller and [sort by the tweets in descending order](http://localhost:8080/emoji/index?sort=tweets&max=10&order=desc). Also in [JSON](http://localhost:8080/emoji.json/index?sort=tweets&max=10&order=desc) and [XML](http://localhost:8080/emoji.xml/index?sort=tweets&max=10&order=desc)
* Top domains of urls in tweets - Top 7 on the [url Controller's index page](http://localhost:8080/url/index); orderd JSON list at [stats page](http://localhost:8080/url/stats).
