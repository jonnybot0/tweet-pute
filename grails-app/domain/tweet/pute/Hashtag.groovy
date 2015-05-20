package tweet.pute

import grails.rest.*
@Resource(uri='/hashtag')
class Hashtag {
    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet
    static transients = ['tweetCount']

    String text
    Date dateCreated
    Date lastUpdated

    static constraints = {
        text unique: true
    }

    def getTweetCount() {
        tweets.size()
    }
}
