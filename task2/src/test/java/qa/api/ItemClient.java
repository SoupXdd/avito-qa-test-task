package qa.api;

import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static qa.api.AvitoRequests.requestAs;

final class ItemClient {

    private static final Pattern ITEM_UUID = Pattern.compile(
            "\\b[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\\b",
            Pattern.CASE_INSENSITIVE);

    private ItemClient() {
    }

    static ValidatableResponse postItem(RequestSpecification request, CreateItemRequest item) {
        return postItem(request.body(item, ObjectMapperType.JACKSON_3));
    }

    static ValidatableResponse postItem(RequestSpecification request, String rawBody) {
        return postItem(request.body(rawBody));
    }

    private static ValidatableResponse postItem(RequestSpecification request) {
        return request.when()
                .post("/api/1/item")
                .then();
    }

    static String createItem(SellerSession seller, CreateItemRequest item) {
        Object status = postItem(requestAs(seller), item)
                .statusCode(200)
                .extract()
                .path("status");

        assertTrue(status instanceof String, "Expected a string status in the creation response");
        return itemIdFromStatus((String) status);
    }

    static String itemIdFromStatus(String status) {
        Matcher matches = ITEM_UUID.matcher(status);
        assertTrue(matches.find(), "No item UUID in the creation status");
        String id = matches.group();
        assertFalse(matches.find(), "Multiple item UUIDs in the creation status");
        return id;
    }

    static ValidatableResponse getItem(RequestSpecification request, String itemId) {
        return request.pathParam("id", itemId)
                .when()
                .get("/api/1/item/{id}")
                .then();
    }

    static ValidatableResponse getSellerItems(RequestSpecification request, int sellerId) {
        return getSellerItems(request, Integer.toString(sellerId));
    }

    static ValidatableResponse getSellerItems(RequestSpecification request, String sellerId) {
        return request.pathParam("sellerId", sellerId)
                .when()
                .get("/api/1/{sellerId}/item")
                .then();
    }

    static ValidatableResponse getStatistics(RequestSpecification request, int version, String itemId) {
        return request.pathParam("version", version)
                .pathParam("id", itemId)
                .when()
                .get("/api/{version}/statistic/{id}")
                .then();
    }

    static ValidatableResponse deleteItem(RequestSpecification request, String itemId) {
        return request.pathParam("id", itemId)
                .when()
                .delete("/api/2/item/{id}")
                .then();
    }
}
