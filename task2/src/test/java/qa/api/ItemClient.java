package qa.api;

import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.UUID;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static qa.api.AvitoRequests.requestAs;

final class ItemClient {

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
        String status = postItem(requestAs(seller), item)
                .statusCode(200)
                .body("status", not(isEmptyOrNullString()))
                .extract()
                .path("status");
        String[] parts = status.split(" - ", 2);
        assertEquals(2, parts.length, "Expected an item UUID after ' - ' in status");

        String id = parts[1];
        assertTrue(UUID.fromString(id).toString().equalsIgnoreCase(id), "Invalid item UUID: " + id);
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
