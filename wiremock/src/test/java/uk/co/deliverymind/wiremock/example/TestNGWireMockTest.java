package uk.co.deliverymind.wiremock.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestNGWireMockTest {

    private WireMockServer wireMockServer;
    private RequestSpecification reqSpec;
    private int port;

    @BeforeClass
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

    @AfterClass
    public void tearDown() {
        wireMockServer.stop();
    }

    private void setUpMocks() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort().dynamicHttpsPort());
        wireMockServer.start();
        port = wireMockServer.port();
    }

    private void setUpReqSpec() {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:" + port)
                .addHeader("Accept", JSON.toString())
                .build();
    }
}
