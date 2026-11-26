package qa.api;

import io.qameta.allure.Allure;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URI;

import static io.restassured.RestAssured.given;

final class AvitoRequests {

    private static final int CONNECT_TIMEOUT_MS = 10_000;
    private static final int READ_TIMEOUT_MS = 30_000;

    private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder()
            .setBaseUri("https://qa-internship.avito.com")
            .setContentType(ContentType.JSON)
            .setConfig(RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("http.connection.timeout", CONNECT_TIMEOUT_MS)
                    .setParam("http.socket.timeout", READ_TIMEOUT_MS)))
            .addFilter((request, response, chain) -> {
                String step = request.getMethod() + " " + URI.create(request.getURI()).getPath();
                return Allure.step(step, context -> {
                    try {
                        Response result = chain.next(request, response);
                        context.parameter("HTTP status", result.statusCode());
                        return result;
                    } catch (Exception failure) {
                        context.parameter("Request error", failure.getClass().getSimpleName());
                        throw failure;
                    }
                });
            })
            .build();

    private AvitoRequests() {
    }

    static RequestSpecification request() {
        return given(REQUEST_SPEC);
    }

    static RequestSpecification requestAs(SellerSession seller) {
        return request().header("Authorization", "Bearer " + seller.token());
    }
}
