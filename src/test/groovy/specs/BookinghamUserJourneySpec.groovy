package specs

import model.User
import org.apache.commons.lang3.RandomStringUtils
import specs.BaseSpecs.BaseSpec
import specs.BaseSpecs.TestableTrait
import spock.lang.Shared
import spock.lang.Stepwise

import java.time.LocalDate

@Stepwise
class BookinghamUserJourneySpec extends BaseSpec implements TestableTrait {

    @Shared
    String username

    @Shared
    User user

    @Shared
    String password

    @Shared
    String hotelName

    @Shared
    String hotelid

    def setupSpec() {
        username = RandomStringUtils.randomAlphabetic(10)
        hotelName = RandomStringUtils.randomAlphabetic(10)
        password = RandomStringUtils.randomAlphanumeric(10)
        println 'generated username: ' + username
        println 'generated hotelName: ' + hotelName
        println 'generated password: ' + password
    }

    def 'create new user'() {
        given: 'a user to create'

        def userToCreate = [
                username : username,
                firstName: 'Jürgen',
                lastName : 'Klinsmann',
                password : password
        ]

        when: 'I post the user'
        def response = client.post {
            request.uri.path = '/users'
            request.body = userToCreate
        }

        user = response.data

        then: 'the user was created'
        assertResponseStatus(response, 201)

        and: 'the response could be mapped to a user'
        user.sayHello()
    }


    def 'create new hotel'() {
        given: 'the recently created user is logged in'
        loginWithUsernameAndPassword(username, password)

        and: 'a hotel to be created'

        def hotel = [
                name    : hotelName,
                street  : 'Theresienhöhe 13',
                zipcode : '80339',
                city    : 'München',
                contact : user.username,
                features: ['MINI_BAR']
        ]

        when: 'I post the hotel'
        def response = client.post {
            request.uri.path = '/hotels'
            request.body = hotel
        }

        hotelid = response.data?.id

        then: 'the hotel was created'
        assertResponseStatus(response, 201)
        assertReceivedDataAreAsExpected(response, hotel)
    }
}