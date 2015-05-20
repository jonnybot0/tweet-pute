package tweet.pute

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Emoji)
class EmojiSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test save"() {
        setup:
            String smiley = new String('F09F9881'.decodeHex())
            new Emoji(text:smiley).save()
        expect:
            Emoji.findAll()[0].text == smiley
    }
}
