package uk.co.automatictester.wiremock.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StandaloneWireMockTest {

    private RequestSpecification reqSpec;
    private int port = 9999;

    @BeforeClass
    public void setUp() {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:" + port)
                .addHeader("Accept", JSON.toString())
                .build();
    }

    @Test(description = "Start WireMock from command line: java -jar wiremock-standalone-2.8.0.jar -port 9999 -v")
    public void verifyGetTest() {
        Response resp = given()
                .spec(reqSpec)
                .when().get("/case/ABC123456")
                .then().extract().response();

        assertThat(resp.getStatusCode(), is(200));
        JsonPath json = new JsonPath(resp.asString());
        assertThat(json.getInt("id"), CoreMatchers.equalTo(1234567890));
    }
}
