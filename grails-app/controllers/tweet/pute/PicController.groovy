package tweet.pute

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PicController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Pic.list(params), model:[picCount: Pic.count()]
    }

    def show(Pic pic) {
        respond pic
    }

    def create() {
        respond new Pic(params)
    }

    @Transactional
    def save(Pic pic) {
        if (pic == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pic.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pic.errors, view:'create'
            return
        }

        pic.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pic.label', default: 'Pic'), pic.id])
                redirect pic
            }
            '*' { respond pic, [status: CREATED] }
        }
    }

    def edit(Pic pic) {
        respond pic
    }

    @Transactional
    def update(Pic pic) {
        if (pic == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pic.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pic.errors, view:'edit'
            return
        }

        pic.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pic.label', default: 'Pic'), pic.id])
                redirect pic
            }
            '*'{ respond pic, [status: OK] }
        }
    }

    @Transactional
    def delete(Pic pic) {

        if (pic == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pic.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pic.label', default: 'Pic'), pic.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pic.label', default: 'Pic'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
