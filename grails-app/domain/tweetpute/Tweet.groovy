package tweetpute

class Tweet {

    static transients = ['emojis', 'hashtags', 'urls', 'pics']

    Long tweetId
    String text

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
