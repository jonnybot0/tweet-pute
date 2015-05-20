package tweet.pute

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TweetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def tweetCount = Tweet.count()
        def firstTweet = Tweet.get(1)
        def lastTweet = Tweet.get(tweetCount)
        //Note that this implementation will calculated the tweets-per-hour of twitter
        // NOT the Tweet Pute app's rate of reception and processing. For that, dateCreated would be more appropriate.
        def spaceBetween = lastTweet.twitterDate - firstTweet.twitterDate
        Map averages
        if(spaceBetween != 0) {
            averages = [perHour: tweetCount/(spaceBetween*24),
                    perMinute: tweetCount/(spaceBetween*24*60),
                    perSecond: tweetCount/(spaceBetween*24*60*60), ]
        }
        else {
            averages = [perHour: 'insufficient data', perMinute: 'insufficient data', perSecond: 'insufficient data',
                 message: 'Please wait a bit while we gather some tweets.']
        }
        respond Tweet.list(params), model:[tweetCount: tweetCount, average: averages]
    }

    def show(Tweet tweet) {
        respond tweet
    }

    def create() {
        respond new Tweet(params)
    }

    @Transactional
    def save(Tweet tweet) {
        if (tweet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tweet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tweet.errors, view:'create'
            return
        }

        tweet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tweet.label', default: 'Tweet'), tweet.id])
                redirect tweet
            }
            '*' { respond tweet, [status: CREATED] }
        }
    }

    def edit(Tweet tweet) {
        respond tweet
    }

    @Transactional
    def update(Tweet tweet) {
        if (tweet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tweet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tweet.errors, view:'edit'
            return
        }

        tweet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tweet.label', default: 'Tweet'), tweet.id])
                redirect tweet
            }
            '*'{ respond tweet, [status: OK] }
        }
    }

    @Transactional
    def delete(Tweet tweet) {

        if (tweet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tweet.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tweet.label', default: 'Tweet'), tweet.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tweet.label', default: 'Tweet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
