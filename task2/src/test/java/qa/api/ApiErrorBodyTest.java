package qa.api;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApiErrorBodyTest {

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
}
