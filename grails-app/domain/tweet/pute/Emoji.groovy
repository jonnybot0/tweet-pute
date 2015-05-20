package tweet.pute

import grails.rest.*
@Resource(uri='/emoji')
class Emoji {

    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet
    static transients = ['tweetCount']
    String text
    Date dateCreated
    Date lastUpdated

    static constraints = { }

    def getTweetCount() {
        tweets.size()
    }
}
