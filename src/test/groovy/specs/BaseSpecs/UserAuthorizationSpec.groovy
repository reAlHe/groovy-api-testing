package specs.BaseSpecs

class UserAuthorizationSpec extends BaseSpec {

    def setupSpec() {
        def user = [
                username: 'alexander.henze',
                password: 's3cr3t'
        ]

        loginAs(user)
    }
}
