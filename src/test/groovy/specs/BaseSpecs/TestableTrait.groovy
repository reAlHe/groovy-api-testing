package specs.BaseSpecs

trait TestableTrait {

    void assertResponseStatus(def response, int expectedResponseCode) {
        assert response?.status == expectedResponseCode
    }

    void assertReceivedDataAreAsExpected(def actualResponse, def expected) {
        expected.each { k, v ->
            assert actualResponse.data[k] == v
        }
    }
}