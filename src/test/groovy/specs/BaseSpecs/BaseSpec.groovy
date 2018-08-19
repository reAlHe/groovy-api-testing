package specs.BaseSpecs

import groovyx.net.http.HttpBuilder
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class BaseSpec extends Specification {

    @Shared
    public static HttpBuilder client

    @Shared
    public String token

    final static String target = System.getProperty('target') ?: 'http://localhost:8080'

    def setupSpec() {
        client = HttpBuilder.configure {
            request.uri = target
            request.setContentType('application/json')
            response.success { resp, data ->
                if (data)
                    [
                            data  : data,
                            status: resp.statusCode
                    ]
                else
                    [
                            status: resp.statusCode
                    ]
            }
            response.failure { resp, data ->
                [
                        stacktrace: data instanceof byte[] ? new String(data) : data,
                        status    : resp.statusCode
                ]
            }
        }
    }

    def loginAs(def user) {
        def response = client.post {
            request.setAccept('application/json')
            request.uri.path = '/authentication/auth'
            request.body = user
        }

        assert response.status == 200
        assert response.data.token

        token = response.data.token

        activateAuthorizationHeader()
    }

    def activateAuthorizationHeader() {
        client.config.parent.request.cookie 'token', token, LocalDateTime.now().plus(1, ChronoUnit.MONTHS)
    }
}
