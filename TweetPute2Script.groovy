@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
import groovyx.net.http.HttpURLClient
@Grab(group='oauth.signpost', module='signpost-core', version='1.2.1.2')
import oauth.signpost.*


def parseOauthConfig(String filePathAndName) {
    new ConfigSlurper().parse(new File(filePathAndName).toURL())
}
def oauth = parseOauthConfig('twitterOauth.groovy')

String sampleEndpoint = "https://stream.twitter.com/1.1/statuses/sample.json"
def twitter = new HttpURLClient(url: sampleEndpoint)
twitter.setOAuth oauth.consumerKey, oauth.consumerSecret, oauth.accessToken, oauth.accessTokenSecret
twitter.request([method: 'GET']).getData()