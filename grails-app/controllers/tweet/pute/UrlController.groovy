package tweet.pute

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import org.hibernate.criterion.CriteriaSpecification

@Transactional(readOnly = true)
class UrlController {
    def tweetPuteService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        List<Url> urls = tweetPuteService.listCollaborators(Url, params)
        List<Map> topDomains = topDomains()?.getAt(1..7)
        respond urls, model:[urlCount: Url.count(), topDomains: topDomains]
    }

    def rank() {
        def domainCount = topDomains()
        render domainCount as JSON
    }

    private List<Map> topDomains() {
        def domainCount = Url.withCriteria {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            projections {
                groupProperty "domain", "domain"
                count "id", 'total'
            }
        }
        List<Map> results = domainCount.sort{a, b -> b.total <=> a.total}
    }

    def show(Url url) {
        respond url
    }

    def create() {
        respond new Url(params)
    }

    @Transactional
    def save(Url url) {
        if (url == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (url.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond url.errors, view:'create'
            return
        }

        url.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'url.label', default: 'Url'), url.id])
                redirect url
            }
            '*' { respond url, [status: CREATED] }
        }
    }

    def edit(Url url) {
        respond url
    }

    @Transactional
    def update(Url url) {
        if (url == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (url.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond url.errors, view:'edit'
            return
        }

        url.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'url.label', default: 'Url'), url.id])
                redirect url
            }
            '*'{ respond url, [status: OK] }
        }
    }

    @Transactional
    def delete(Url url) {

        if (url == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        url.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'url.label', default: 'Url'), url.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'url.label', default: 'Url'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
