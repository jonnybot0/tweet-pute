package tweet.pute

import grails.rest.*
@Resource(uri='/pic')
class Pic {
    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet
    static transients = ['tweetCount']
    String text
    Date dateCreated
    Date lastUpdated
    static constraints = {
        text size: 1..2038 //ovverride default max of 255
    }

    def getTweetCount() {
        tweets.size()
    }
}