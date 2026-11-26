package qa.api;

import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
        Map<?, ?> body = authorizationBody(authorize(credentials).statusCode(200));
        Object token = body.get("accessToken");
        assertTrue(token instanceof String && !((String) token).isBlank(),
                "Expected a nonblank accessToken in the authorization response");
        assertTrue("Bearer".equals(body.get("tokenType")), "Expected Bearer tokenType");
        return (String) token;
    }

    static void checkRejectedAuthorization(ValidatableResponse response, int expectedStatus) {
        Map<?, ?> body = authorizationBody(response.statusCode(expectedStatus));
        assertFalse(body.containsKey("accessToken"), "Rejected authorization must not return accessToken");
    }

    private static Map<?, ?> authorizationBody(ValidatableResponse response) {
        Object body;
        try {
            body = response.extract().jsonPath().get("$");
        } catch (RuntimeException ignored) {
            return fail("Expected an authorization response containing a JSON object");
        }
        if (body instanceof Map<?, ?> object) {
            return object;
        }
        return fail("Expected an authorization response containing a JSON object");
    }

    static SellerSession createSeller() {
        TestCredentials credentials = TestCredentials.newUser();
        int sellerId = registerAndGetId(credentials);
        String token = authorizeAndGetToken(credentials);
        return new SellerSession(sellerId, token);
    }
}
