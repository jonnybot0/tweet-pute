package tweet.pute

import java.net.URL
import grails.rest.*
@Resource(uri='/url')
class Url {

    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet
    static transients = ['tweetCount']
    String text
    String domain
    Date dateCreated
    Date lastUpdated
    static constraints = {
        text size: 1..2038 //ovverride default max of 255
        domain nullable: true //Will always be set by beforeValidate() method
    }

    def beforeValidate() {
        URL tempURL = new URL(text)
        domain = tempURL.getHost()
    }

    def getTweetCount() {
        tweets.size()
    }
}
