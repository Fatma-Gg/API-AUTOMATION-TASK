package APIAssessment;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.notNullValue;

public class ApiAutomationTest {

        @Test
        public void validateAuthToken() {
            // Set base URI
            RestAssured.baseURI = "https://restful-booker.herokuapp.com";


            // Send POST request and capture the response
            Response response = RestAssured
                    .given().log().all()
                    .header("Content-Type", "application/json")
                    .body(Payload.body())
                    .when().post("/auth")
                    .then().log().all()
                           .assertThat().statusCode(200)
                           .body("token",notNullValue()).extract().response();


                            // More Validation ^_^

            // Validate that the status code is 200
            Assert.assertEquals(response.getStatusCode(), 200, "Status Code is not 200!");

            // Validate that the response is not empty
            Assert.assertFalse(response.getBody().asString().isEmpty(), "Response body is empty!");

            JsonPath js = new JsonPath(response.asString());
            String token = js.getString("token");


            // Validate that the token is present
            Assert.assertNotNull(token, "Token not found in the response!");

            // Congrats !!  here is our token
            System.out.println("Token generated: " + token);
        }
    }

