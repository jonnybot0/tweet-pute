package tweet.pute

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Url)
class UrlSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def "test length"() {
        when:"fix me"
            String exampleText = 'http://winnski.com/bs/'
            def url = new Url(text: exampleText)
            url.save()
        then:
            url.id //object persisted
        when:
            def aBunchOfAs = (1..2045).collect{'a'}.join('')
            String tooLongURL = "http://${aBunchOfAs}.net".toString()
            def tooLongUrl = new Url(text: tooLongURL)
            def saveResult = tooLongUrl.save()
            def error = tooLongUrl.errors.getFieldError('text')
        then:
            error
            !error.isBindingFailure() //it's a validation error
            error.getDefaultMessage().contains('size')
            !saveResult
    }
}
