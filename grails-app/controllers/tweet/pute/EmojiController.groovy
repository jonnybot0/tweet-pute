package tweet.pute

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmojiController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Emoji.list(params), model:[emojiCount: Emoji.count()]
    }

    def show(Emoji emoji) {
        respond emoji
    }

    def create() {
        respond new Emoji(params)
    }

    @Transactional
    def save(Emoji emoji) {
        if (emoji == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emoji.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emoji.errors, view:'create'
            return
        }

        emoji.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'emoji.label', default: 'Emoji'), emoji.id])
                redirect emoji
            }
            '*' { respond emoji, [status: CREATED] }
        }
    }

    def edit(Emoji emoji) {
        respond emoji
    }

    @Transactional
    def update(Emoji emoji) {
        if (emoji == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emoji.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emoji.errors, view:'edit'
            return
        }

        emoji.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'emoji.label', default: 'Emoji'), emoji.id])
                redirect emoji
            }
            '*'{ respond emoji, [status: OK] }
        }
    }

    @Transactional
    def delete(Emoji emoji) {

        if (emoji == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        emoji.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'emoji.label', default: 'Emoji'), emoji.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'emoji.label', default: 'Emoji'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
