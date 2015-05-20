package tweet.pute

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HashtagController {
    def tweetPuteService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        List<Hashtag> hashtags = tweetPuteService.listCollaborators(Hashtag, params)
        respond hashtags, model:[hashtagCount: Hashtag.count()]
    }

    def show(Hashtag hashtag) {
        respond hashtag
    }

    def create() {
        respond new Hashtag(params)
    }

    @Transactional
    def save(Hashtag hashtag) {
        if (hashtag == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (hashtag.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond hashtag.errors, view:'create'
            return
        }

        hashtag.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'hashtag.label', default: 'Hashtag'), hashtag.id])
                redirect hashtag
            }
            '*' { respond hashtag, [status: CREATED] }
        }
    }

    def edit(Hashtag hashtag) {
        respond hashtag
    }

    @Transactional
    def update(Hashtag hashtag) {
        if (hashtag == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (hashtag.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond hashtag.errors, view:'edit'
            return
        }

        hashtag.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'hashtag.label', default: 'Hashtag'), hashtag.id])
                redirect hashtag
            }
            '*'{ respond hashtag, [status: OK] }
        }
    }

    @Transactional
    def delete(Hashtag hashtag) {

        if (hashtag == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        hashtag.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'hashtag.label', default: 'Hashtag'), hashtag.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'hashtag.label', default: 'Hashtag'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
