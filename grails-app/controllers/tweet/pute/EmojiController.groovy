package tweet.pute

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmojiController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        List<Emoji> emojis = []
        if (params.sort == 'tweets') {
            String ascOrDesc = params.order == 'asc' ? 'asc' : 'desc'
            emojis = Emoji.executeQuery("""SELECT emoji
                from Emoji emoji
                order by size(emoji.tweets) $ascOrDesc """, [:], params)
        }
        else {emojis = Emoji.list(params)}
        respond emojis, model:[emojiCount: Emoji.count()]
    }

    def show(Emoji emoji) {
        respond emoji
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
