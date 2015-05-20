package tweet.pute

import grails.rest.*
@Resource(uri='/url')
class Url {

    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet
    String text
    Date dateCreated
    Date lastUpdated
    static constraints = {
        text size: 1..2038 //ovverride default max of 255
    }
}
