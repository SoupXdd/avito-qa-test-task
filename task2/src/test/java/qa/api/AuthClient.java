package qa.api;

import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static qa.api.AvitoRequests.request;

final class AuthClient {

    private AuthClient() {
    }

    static ValidatableResponse register(Object body) {
        return request()
                .body(body, ObjectMapperType.JACKSON_3)
                .when()
                .post("/api/1/register")
                .then()
                .log().status();
    }

    static ValidatableResponse authorize(Object body) {
        return request()
                .body(body, ObjectMapperType.JACKSON_3)
                .when()
                .post("/api/1/autorize")
                .then()
                .log().status();
    }

    static int registerAndGetId(TestCredentials credentials) {
        return register(credentials)
                .statusCode(201)
                .body("id", instanceOf(Number.class))
                .body("username", equalTo(credentials.username()))
                .extract()
                .jsonPath()
                .getInt("id");
    }

    static String authorizeAndGetToken(TestCredentials credentials) {
        return authorize(credentials)
                .statusCode(200)
                .body("accessToken", not(isEmptyOrNullString()))
                .body("tokenType", equalTo("Bearer"))
                .extract()
                .path("accessToken");
    }

    static SellerSession createSeller() {
        TestCredentials credentials = TestCredentials.newUser();
        int sellerId = registerAndGetId(credentials);
        String token = authorizeAndGetToken(credentials);
        return new SellerSession(sellerId, token);
    }
}
