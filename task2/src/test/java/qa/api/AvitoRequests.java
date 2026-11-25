package qa.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

final class AvitoRequests {

    private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder()
            .setBaseUri("https://qa-internship.avito.com")
            .setContentType(ContentType.JSON)
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
