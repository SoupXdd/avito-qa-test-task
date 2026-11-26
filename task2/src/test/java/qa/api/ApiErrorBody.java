package qa.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertNull;

final class ApiErrorBody {

    private ApiErrorBody() {
    }

    static void checkNoFields(ValidatableResponse response, int expectedStatus, Set<String> fields) {
        response.statusCode(expectedStatus);
        String field = findField(response.extract().asString(), fields);
        assertNull(field, () -> "Error response contains data field: " + field);
    }

    static void checkNoData(ValidatableResponse response, Set<String> fields) {
        String field = findField(response.extract().asString(), fields, Objects::nonNull);
        assertNull(field, () -> "Error response contains data field: " + field);
    }

    static String findField(String body, Set<String> fields) {
        return findField(body, fields, value -> true);
    }

    private static String findField(String body, Set<String> fields, Predicate<Object> matchesValue) {
        String text = body.stripLeading();
        if (!text.startsWith("{") && !text.startsWith("[")) {
            return null;
        }
        Object root = new JsonPath(text).get("$");
        Map<?, ?> validationErrors = null;
        if (root instanceof Map<?, ?> object && object.get("errors") instanceof Map<?, ?> errors) {
            validationErrors = errors;
        }
        return findFieldInJson(root, fields, validationErrors, matchesValue);
    }

    private static String findFieldInJson(Object value, Set<String> fields, Map<?, ?> validationErrors,
                                          Predicate<Object> matchesValue) {
        if (value instanceof Map<?, ?> object) {
            for (Map.Entry<?, ?> entry : object.entrySet()) {
                if (object == validationErrors && entry.getValue() instanceof String) {
                    continue;
                }
                if (fields.contains(entry.getKey()) && matchesValue.test(entry.getValue())) {
                    return (String) entry.getKey();
                }
                String nestedField = findFieldInJson(entry.getValue(), fields, validationErrors, matchesValue);
                if (nestedField != null) {
                    return nestedField;
                }
            }
        } else if (value instanceof List<?> list) {
            for (Object element : list) {
                String nestedField = findFieldInJson(element, fields, validationErrors, matchesValue);
                if (nestedField != null) {
                    return nestedField;
                }
            }
        }
        return null;
    }
}
