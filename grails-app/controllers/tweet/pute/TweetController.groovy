package tweet.pute

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON

@Transactional(readOnly = true)
class TweetController {
    def tweetPuteService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        Integer tweetCount = Tweet.count()
        Map averages = tweetPuteService.getTweetCollectionRates(tweetCount)
        Map stats = tweetPuteService.getCollaboratorStats(tweetCount)
        respond Tweet.list(params), model:[tweetCount: tweetCount, average: averages, stats: stats]
    }

    def stats() {
        def tweetCount = Tweet.count()
        Map averages = tweetPuteService.getTweetCollectionRates(tweetCount)
        Map stats = tweetPuteService.getCollaboratorStats(tweetCount)
        Map data = [average: averages, stats: stats, total: tweetCount]
        render data as JSON
    }

    def show(Tweet tweet) {
        respond tweet
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
