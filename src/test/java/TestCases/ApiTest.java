package TestCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTest {

    private String baseURL = "http://hiring.petroapp.com/api.php";
    private String sessionId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseURL;
    }

    @Test(dataProvider = "validUsers")
    public void authenticateValidUsers(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(params)
                .post("?endpoint=authenticate");

        Assert.assertEquals(response.getStatusCode(), 200);
        sessionId = response.jsonPath().getString("session_id");
        Assert.assertNotNull(sessionId);
    }

    @Test(dependsOnMethods = "authenticateValidUsers", dataProvider = "validUsers")
    public void fetchCarsValidSession(String username, String password) {
        authenticateValidUsers(username, password);
        Response response = given()
                .header("Session-Id", sessionId)
                .get("?endpoint=fetch_cars");

        Assert.assertEquals(response.getStatusCode(), 200);
        String[] cars = response.jsonPath().getString("cars").split(",");
        Assert.assertTrue(cars.length > 0);
    }

    @Test(dataProvider = "invalidScenarios")
    public void handleInvalidScenarios(String username, String password, boolean invalidSession) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        Response authResponse = given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(params)
                .post("?endpoint=authenticate");

        if (!invalidSession) {
            Assert.assertEquals(authResponse.getStatusCode(), 401);
            String errorMessage = authResponse.jsonPath().getString("error");
            Assert.assertNotNull(errorMessage);
        } else {
            Assert.assertEquals(authResponse.getStatusCode(), 200);
            sessionId = authResponse.jsonPath().getString("session_id");
            Assert.assertNotNull(sessionId);
            Response fetchResponse = given()
                    .header("Session-Id", "invalidSessionId")
                    .get("?endpoint=fetch_cars");

            Assert.assertEquals(fetchResponse.getStatusCode(), 401);
            String errorMessage = fetchResponse.jsonPath().getString("error");
            Assert.assertEquals(errorMessage, "Invalid session ID");
            System.out.println(errorMessage);
        }
    }

    @DataProvider(name = "validUsers")
    public Object[][] validUsers() {
        return new Object[][]{
                {"user1", "password123"},
                {"user2", "password456"},
                {"admin", "adminpass"},
        };
    }

    @DataProvider(name = "invalidScenarios")
    public Object[][] invalidScenarios() {
        return new Object[][]{
                {"invalidUser", "wrongPassword", false},
                {"user1", "password123", true},
        };
    }
}
