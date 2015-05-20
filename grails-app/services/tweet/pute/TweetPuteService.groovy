package tweet.pute

import grails.transaction.Transactional
import groovy.time.TimeCategory

@Transactional
class TweetPuteService {

    List listCollaborators(Class domainClass, params) {
        List collaborators = []
        if (params.sort == 'tweets') {
            String ascOrDesc = params.order == 'asc' ? 'asc' : 'desc'
            String lowerName = domainClass.simpleName.toLowerCase()
            collaborators = domainClass.executeQuery("""SELECT $lowerName
                from ${domainClass.simpleName} $lowerName
                order by size(${lowerName}.tweets) $ascOrDesc """, [:], params)
        }
        else {collaborators = domainClass.list(params)}
        return collaborators
    }

    Map getTweetCollectionRates(Integer tweetCount) {
        def firstTweet = Tweet.get(1)
        def lastTweet = Tweet.get(tweetCount)
        def spaceBetween = TimeCategory.minus(lastTweet.dateCreated, firstTweet.dateCreated)
        if(spaceBetween.seconds != 0) {
            return [perHour: (tweetCount * 60 * 60)/(spaceBetween.seconds),
                        perMinute: (tweetCount * 60)/(spaceBetween.seconds),
                        perSecond: tweetCount/(spaceBetween.seconds), ]
        }
        else {
            return [perHour: 'insufficient data', perMinute: 'insufficient data', perSecond: 'insufficient data',
                        message: 'Please wait a bit while we gather some tweets.']
        }
    }

    Map getCollaboratorStats(Integer tweetCount) {
        [withPics: Tweet.countByPicsIsNotEmpty(),
            withUrls: Tweet.countByUrlsIsNotEmpty(),
            withEmojis: Tweet.countByEmojisIsNotEmpty()].collectEntries{
            [(it.key): it.value/tweetCount]
        }
    }
}
