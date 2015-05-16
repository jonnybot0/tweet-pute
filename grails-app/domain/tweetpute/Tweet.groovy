package tweetpute

class Tweet {

    static transients = ['emojis', 'hashtags', 'urls', 'pics']

    Long tweetId
    String text
    String user
    Date twitterDate
    Date dateCreated

    static constraints = {
    }

    def getEmojis() {

    }

    def getHashtags() {

    }

    def getUrls() {

    }

    def getPics() {

    }
}
