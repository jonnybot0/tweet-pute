package tweet.pute

class Hashtag {

    String text

    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet
    static constraints = {
        text unique: true
    }
}
