package specs


import org.apache.commons.lang3.RandomStringUtils
import specs.BaseSpecs.TestableTrait
import specs.BaseSpecs.UserAuthorizationSpec
import spock.lang.Shared
import spock.lang.Stepwise

import java.time.LocalDate

@Stepwise
class BookinghamUserJourneySpec extends UserAuthorizationSpec implements TestableTrait {

    @Shared
    String username

    @Shared
    String hotelName

    @Shared
    String hotelid

    def setupSpec() {
        username = RandomStringUtils.randomAlphabetic(10)
        hotelName = RandomStringUtils.randomAlphabetic(10)
        println 'generated hotelName: ' + hotelName
    }

    def 'create new user'() {
        given: 'a user to create'

        def user = [
                username : username,
                firstName: 'Jürgen',
                lastName : 'Klinsmann',
                password : '12345abc'
        ]

        when: 'I post the user'
        def response = client.post {
            request.uri.path = '/users'
            request.body = user
        }

        // User usr = response.data

        then: 'the user was created'
        assertResponseStatus(response, 201)
    }


    def 'create new hotel'() {
        given: 'a hotel to be created'

        def hotel = [
                name    : hotelName,
                street  : 'Theresienhöhe 13',
                zipcode : '80339',
                city    : 'München',
                contact : 'alexander.henze',
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


    def 'make a booking for hotel'() {
        given: 'booking data is known'
        String start = LocalDate.now().toString()
        String end = LocalDate.now().plusDays(14).toString()

        def booking = [
                hotelId  : hotelid,
                startDate: start,
                endDate  : end,
        ]

        when: 'I post the booking'
        def response = client.post {
            request.uri.path = '/bookings'
            request.body = booking
        }

        then: 'the booking was successfully created for the user'
        assertResponseStatus(response, 201)

        assert response.data?.booker == 'alexander.henze'
        assert response.data.hotel.name == hotelName
        assert response.data.startDate == start
        assert response.data.endDate == end
    }

}