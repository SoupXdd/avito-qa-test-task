package qa.api;

import io.restassured.builder.ResponseBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthClientTest {

    @Test
    void shouldReportUnexpectedTokenWithoutItsValue() {
        var response = new ResponseBuilder().setStatusCode(401).setContentType("application/json")
                .setBody("""
                        {"accessToken":"synthetic-review-marker"}
                        """)
                .build().then();

        AssertionError failure = assertThrows(AssertionError.class,
                () -> AuthClient.checkRejectedAuthorization(response, 401));

        assertTrue(failure.getMessage().contains("Rejected authorization must not return accessToken"));
        assertFalse(failure.getMessage().contains("synthetic-review-marker"), "Assertion exposed token value");
    }
}
