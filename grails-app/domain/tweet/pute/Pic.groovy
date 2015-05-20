package tweet.pute

import grails.rest.*
@Resource(uri='/pic')
class Pic extends Url {
    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet

    static constraints = {
    }
}
