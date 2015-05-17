package tweet.pute

class Emoji {

    static hasMany = [tweets: Tweet]
    static belongsTo = Tweet
    String text

    static constraints = {
    }
}
