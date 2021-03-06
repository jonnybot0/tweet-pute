package tweet.pute

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Pic)
@Mock(Url)
class PicSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test saving"() {
        setup:
            def pic = new Pic(text: 'http://pic.twitter.com/foo')
            def saveResult = pic.save()
        expect:""
            pic instanceof Pic
            !(pic instanceof Url)
            saveResult
            Pic.list()[0].id == pic.id
    }
}
