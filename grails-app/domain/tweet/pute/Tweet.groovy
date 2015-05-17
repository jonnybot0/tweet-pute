package tweet.pute

class Tweet {

    static hasMany = [hashtags: Hashtag, emojis: Emoji, urls: Url, pics: Pic]

    Long tweetId
    String text
    String user
    Date twitterDate
    Date dateCreated

    static constraints = {
    }
}
