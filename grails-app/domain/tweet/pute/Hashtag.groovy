package tweet.pute

import grails.rest.*
@Resource(uri='/hashtag')
class Hashtag {
    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet

    String text
    Date dateCreated
    Date lastUpdated

    static constraints = {
        text unique: true
    }
}
