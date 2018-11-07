package json;

import java.util.Collections;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.given;

public class JavaTest {

    @Test
    public void createUser() throws JSONException {
        String jsonString = new JSONObject()
                .put("username", "jogi.loew")
                .put("firstName", "Joachim")
                .put("lastName", "Löw")
                .put("password", "Weltmeister!")
                .toString();

        given().contentType("application/json").baseUri("http://localhost:8080").body(jsonString).when().post(
                "/users").then().statusCode(201);

    }


    @Test
    public void createHotel() throws JSONException {
        String jsonString = new JSONObject()
                .put("name", "Worst bed in town")
                .put("street", "Am Abstieg 1")
                .put("zipcode", "99999")
                .put("city", "Aue")
                .put("contact", "jogi.loew")
                .put("features", Collections.singletonList("MINI_BAR"))
                .toString();

        given().cookie("token", "c8xpCThtA8PI0pqF").contentType("application/json").baseUri(
                "http://localhost:8080").header("token", "").body(jsonString).when().post("/hotels").then().statusCode(
                201);

    }

}
