package qa.api;

import io.restassured.builder.ResponseBuilder;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiErrorBodyTest {

    @Test
    void shouldAllowItemIdMentionedInAccessDeniedMessage() {
        ValidatableResponse denied = response(401, """
                {"result":{"message":"Access denied for item 87f9403d-d4fc-46fd-a290-f4c642872f7d"}}
                """);

        ApiErrorBody.checkNoFields(denied, 401, Set.of("id", "name"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"QA item\"}",
            "{\"result\":[{\"id\":\"87f9403d-d4fc-46fd-a290-f4c642872f7d\"}]}"
    })
    void shouldRejectItemDataInAccessDeniedBody(String body) {
        AssertionError failure = assertThrows(AssertionError.class,
                () -> ApiErrorBody.checkNoFields(response(401, body), 401, Set.of("id", "name")));

        assertTrue(failure.getMessage().contains("Error response contains data field:"));
    }

    @Test
    void shouldRejectUnexpectedStatusEvenWithoutItemData() {
        assertThrows(AssertionError.class,
                () -> ApiErrorBody.checkNoFields(response(200, "{}"), 401, Set.of("id", "name")));
    }

    @Test
    void shouldDetectNestedUserDataInRegistrationError() {
        ValidatableResponse response = badRequest("""
                {"result":{"id":null,"users":[{"username":"qa_test"}]}}
                """);

        AssertionError failure = assertThrows(AssertionError.class,
                () -> ApiErrorBody.checkNoData(response, Set.of("id", "username")));

        assertTrue(failure.getMessage().contains("data field: username"));
    }

    @Test
    void shouldAllowNullUserFieldsAndValidationMessages() {
        ApiErrorBody.checkNoData(badRequest("""
                {"id":null,"result":{"username":null},"errors":{"username":"required"}}
                """), Set.of("id", "username"));
    }

    @Test
    void shouldAllowFieldNameMentionedInErrorMessage() {
        String body = """
                {"result":{"message":"Invalid field: \\"name\\""}}
                """;

        assertNull(ApiErrorBody.findField(body, Set.of("id", "name")));
    }

    @Test
    void shouldAllowValidationMessageKeyedByFieldName() {
        String body = """
                {"errors":{"name":"required","likes":"must be positive"}}
                """;

        assertNull(ApiErrorBody.findField(body, Set.of("name", "likes")));
    }

    @Test
    void shouldDetectNestedDataInsideValidationErrors() {
        String body = """
                {"errors":{"name":"required","details":[{"likes":21}]}}
                """;

        assertEquals("likes", ApiErrorBody.findField(body, Set.of("name", "likes")));
    }

    @Test
    void shouldDetectDataAlongsideValidationErrors() {
        String body = """
                {"errors":{"name":"required"},"result":[{"name":"QA item"}]}
                """;

        assertEquals("name", ApiErrorBody.findField(body, Set.of("name")));
    }

    @Test
    void shouldDetectItemIdInsideNestedArray() {
        String body = """
                {"result":[{"id":"87f9403d-d4fc-46fd-a290-f4c642872f7d"}]}
                """;

        assertEquals("id", ApiErrorBody.findField(body, Set.of("id", "name")));
    }

    @Test
    void shouldDetectCounterInsideNestedObject() {
        String body = """
                {"result":{"statistics":{"likes":21}}}
                """;

        assertEquals("likes", ApiErrorBody.findField(body, Set.of("likes", "viewCount", "contacts")));
    }

    @Test
    void shouldAllowEmptyAndPlainTextErrorBodies() {
        Set<String> fields = Set.of("id", "name");

        assertNull(ApiErrorBody.findField("", fields));
        assertNull(ApiErrorBody.findField("Bad request", fields));
    }

    private ValidatableResponse badRequest(String body) {
        return response(400, body);
    }

    private ValidatableResponse response(int status, String body) {
        return new ResponseBuilder().setStatusCode(status).setContentType("application/json")
                .setBody(body).build().then();
    }
}
