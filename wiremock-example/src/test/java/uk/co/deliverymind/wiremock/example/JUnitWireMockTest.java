package uk.co.deliverymind.wiremock.example;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JUnitWireMockTest {

    private RequestSpecification reqSpec;
    private int port;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());

    @Before
    public void setUp() {
        setUpMocks();
        setUpReqSpec();
    }

    @Test
    public void verifyGetTest() {
        Response resp = given()
                .spec(reqSpec)
                .when().get("/case/ABC123456")
                .then().extract().response();

        assertThat(resp.getStatusCode(), is(200));
        JsonPath json = new JsonPath(resp.asString());
        assertThat(json.getInt("id"), CoreMatchers.equalTo(1234567890));
    }

    private void setUpMocks() {
        port = wireMockRule.port();

        stubFor(get(urlMatching("/case/[A-Z0-9]+"))
                .withHeader("Accept", equalTo(JSON.toString()))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": 1234567890 }")));
    }

    private void setUpReqSpec() {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:" + port)
                .addHeader("Accept", JSON.toString())
                .build();
    }
}
