package tweet.pute

class Tweet {

    static hasMany = [hashtags: Hashtag, emojis: Emoji, urls: Url, pics: Pic]

    Long tweetId
    String text
    String user
    Date twitterDate
    Date dateCreated
    Date lastUpdated

    static constraints = {
        user nullable: true
        twitterDate nullable: true
    }
}
