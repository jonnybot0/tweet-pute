package tweet.pute

class Url {

    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet
    String text
    static constraints = {
    }
}
