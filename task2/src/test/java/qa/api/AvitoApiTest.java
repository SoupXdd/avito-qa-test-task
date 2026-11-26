package qa.api;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static qa.api.AuthClient.authorize;
import static qa.api.AuthClient.authorizeAndGetToken;
import static qa.api.AuthClient.createSeller;
import static qa.api.AuthClient.register;
import static qa.api.AuthClient.registerAndGetId;
import static qa.api.AvitoRequests.request;
import static qa.api.AvitoRequests.requestAs;
import static qa.api.CreateItemRequest.testItem;
import static qa.api.ItemClient.createItem;
import static qa.api.ItemClient.deleteItem;
import static qa.api.ItemClient.getItem;
import static qa.api.ItemClient.getSellerItems;
import static qa.api.ItemClient.getStatistics;
import static qa.api.ItemClient.postItem;
import static qa.api.TestCredentials.DEFAULT_PASSWORD;

@ExtendWith(ApiFailureAttachment.class)
public class AvitoApiTest {

    private static final String OTHER_PASSWORD = "another_strong_password";
    private static final String REJECTION_FIXTURE_ITEM_NAME = "QA стол для проверки";
    private static final Set<String> ITEM_FIELDS = Set.of("id", "sellerId", "name", "price", "statistics", "createdAt");
    private static final Set<String> STATISTICS_FIELDS = Set.of("likes", "viewCount", "contacts");

    @Test
    void registerReturnCreatedUser() {
        registerAndGetId(TestCredentials.newUser());
    }

    @Test
    void shouldRejectRegistrationWithoutUsername() {
        checkInvalidRegistration(Map.of("password", DEFAULT_PASSWORD));
    }

    @Test
    void shouldRejectRegistrationWithEmptyUserName() {
        checkInvalidRegistration(new TestCredentials("", DEFAULT_PASSWORD));
    }

    @Test
    void shouldRejectRegistrationWithDoubleRegistration() {
        TestCredentials credentials = TestCredentials.newUser();
        TestCredentials changedCredentials = credentials.withPassword(OTHER_PASSWORD);
        registerAndGetId(credentials);

        register(changedCredentials).statusCode(409);

        authorizeAndGetToken(credentials);
        authorize(changedCredentials)
                .statusCode(401)
                .body("$", not(hasKey("accessToken")));
    }

    @Test
    void authReturnUser() {
        SellerSession seller = createSeller();

        getSellerItems(requestAs(seller), seller.id())
                .statusCode(200)
                .body("$", equalTo(List.of()));
    }

    @Test
    void shouldRejectRegistrationWithoutPassword() {
        TestCredentials credentials = TestCredentials.newUser();
        checkInvalidRegistration(Map.of("username", credentials.username()));
    }

    @Test
    void shouldRejectRegistrationWithEmptyPassword() {
        checkInvalidRegistration(TestCredentials.newUser().withPassword(""));
    }

    @Test
    void shouldRejectAuthorizationWithWrongPassword() {
        TestCredentials credentials = TestCredentials.newUser();
        registerAndGetId(credentials);

        authorize(credentials.withPassword(OTHER_PASSWORD))
                .statusCode(401)
                .body("$", not(hasKey("accessToken")));
    }

    @Test
    void shouldRejectAuthorizationWithUnknownUsername() {
        authorize(TestCredentials.newUser())
                .statusCode(401)
                .body("$", not(hasKey("accessToken")));
    }

    @Test
    void shouldRejectAuthorizationWithoutUsername() {
        TestCredentials credentials = TestCredentials.newUser();
        registerAndGetId(credentials);

        authorize(Map.of("password", credentials.password()))
                .statusCode(400)
                .body("$", not(hasKey("accessToken")));
    }

    @Test
    void shouldRejectAuthorizationWithoutPassword() {
        TestCredentials credentials = TestCredentials.newUser();
        registerAndGetId(credentials);

        authorize(Map.of("username", credentials.username()))
                .statusCode(400)
                .body("$", not(hasKey("accessToken")));
    }

    @Test
    void shouldCreateAndReadItemById() {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA стол " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            checkSavedItem(seller, itemId, item);
        }
    }

    @Test
    void shouldCompleteItemLifecycle() {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA item lifecycle " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller, true)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            UUID itemUuid = UUID.fromString(itemId);

            checkSavedItem(seller, itemId, item);
            checkSavedStatistics(getStatistics(requestAs(seller), 1, itemId), item.statistics(), true);
            checkSavedStatistics(getStatistics(requestAs(seller), 2, itemId), item.statistics(), false);

            deleteItem(requestAs(seller), itemId).statusCode(200);
            checkItemLookupRejected(getItem(requestAs(seller), itemId), 404);
            cleanup.markDeleted(itemUuid);
        }
    }

    @Test
    void shouldAcceptApi11FixtureWithBadRequestAndNoNewItem() {
        checkApi11Fixture(400, List.of("Старое объявление"), List.of());
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 401, 403, 500})
    void shouldRejectApi11FixtureWithUnexpectedStatus(int status) {
        assertThrows(AssertionError.class, () -> checkApi11Fixture(status, List.of(), List.of()));
    }

    @Test
    void shouldRejectApi11FixtureWhenItemAppears() {
        List<String> listWithItem = List.of(REJECTION_FIXTURE_ITEM_NAME);

        assertAll(
                () -> assertThrows(AssertionError.class,
                        () -> checkApi11Fixture(400, listWithItem, List.of())),
                () -> assertThrows(AssertionError.class,
                        () -> checkApi11Fixture(400, List.of(), listWithItem)));
    }

    @Test
    void shouldCreateSeparateItemsFromIdenticalRequests() {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA identical item " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller)) {
            String firstId = createTrackedItem(cleanup, seller, item);
            String secondId = createTrackedItem(cleanup, seller, item);

            assertNotEquals(UUID.fromString(firstId), UUID.fromString(secondId),
                    "Identical requests should create items with different UUIDs");
            checkSavedItem(seller, firstId, item);
            checkSavedItem(seller, secondId, item);
        }
    }

    @Test
    void shouldKeepIdenticallyNamedItemsWithTheirOwnSellers() {
        SellerSession firstSeller = createSeller();
        SellerSession secondSeller = createSeller();
        String itemName = "QA same name " + firstSeller.id();
        CreateItemRequest firstItem = testItem(firstSeller, itemName);
        CreateItemRequest secondItem = testItem(secondSeller, itemName);
        try (ItemCleanup firstCleanup = cleanupFor(firstSeller);
             ItemCleanup secondCleanup = cleanupFor(secondSeller)) {
            String firstId = createTrackedItem(firstCleanup, firstSeller, firstItem);
            String secondId = createTrackedItem(secondCleanup, secondSeller, secondItem);

            assertNotEquals(UUID.fromString(firstId), UUID.fromString(secondId), "Each seller should have a separate item");
            checkSavedItem(firstSeller, firstId, firstItem);
            checkSavedItem(secondSeller, secondId, secondItem);
            getSellerItems(requestAs(firstSeller), firstSeller.id())
                    .statusCode(200)
                    .body("id", equalTo(List.of(firstId)));
            getSellerItems(requestAs(secondSeller), secondSeller.id())
                    .statusCode(200)
                    .body("id", equalTo(List.of(secondId)));
        }
    }

    @Test
    void shouldReturnNotFoundForMissingItem() {
        SellerSession seller = createSeller();
        String missingId = UUID.randomUUID().toString();

        checkItemLookupRejected(getItem(requestAs(seller), missingId), 404);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "not-a-uuid",
            "123e4567-e89b-12d3-a456-42661417400",
            "123e4567-e89b-12d3-a456-4266141740000",
            "123e4567-e89b-12d3-a456-42661417400g"
    })
    void shouldRejectItemLookupWithMalformedUuid(String malformedUuid) {
        SellerSession seller = createSeller();

        checkItemLookupRejected(getItem(requestAs(seller), malformedUuid), 400);
    }

    @ParameterizedTest
    @ValueSource(strings = {"malformed JSON", "missing sellerID", "sellerID object"})
    void shouldRejectInvalidItemCreation(String variant) {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA invalid item " + seller.id());
        ItemStatistics statistics = item.statistics();
        String itemFields = """
                "name": "%s", "price": %d,
                "statistics": {"likes": %d, "viewCount": %d, "contacts": %d}
                """.formatted(item.name(), item.price(), statistics.likes(), statistics.viewCount(), statistics.contacts());
        String body = switch (variant) {
            case "malformed JSON" -> "{\"sellerID\": " + seller.id() + ", " + itemFields;
            case "missing sellerID" -> "{" + itemFields + "}";
            case "sellerID object" -> "{\"sellerID\": {}, " + itemFields + "}";
            default -> throw new IllegalArgumentException("Unknown item variant: " + variant);
        };

        checkRejectedCreation(seller, item.name(), 400, () -> postItem(requestAs(seller), body));
    }

    @Test
    void shouldListOnlyItemsOfRequestedSeller() {
        SellerSession seller = createSeller();
        SellerSession otherSeller = createSeller();
        try (ItemCleanup cleanup = cleanupFor(seller);
             ItemCleanup otherCleanup = cleanupFor(otherSeller)) {
            String otherId = createTrackedItem(otherCleanup, otherSeller,
                    testItem(otherSeller, "QA other seller " + otherSeller.id()));

            getSellerItems(requestAs(seller), seller.id())
                    .statusCode(200)
                    .body("$", equalTo(List.of()));

            String firstId = createTrackedItem(cleanup, seller,
                    testItem(seller, "QA first list item " + seller.id()));
            getSellerItems(requestAs(seller), seller.id())
                    .statusCode(200)
                    .body("$", instanceOf(List.class))
                    .body("id", equalTo(List.of(firstId)))
                    .body("id", not(hasItem(otherId)));

            String secondId = createTrackedItem(cleanup, seller,
                    testItem(seller, "QA second list item " + seller.id()));
            assertNotEquals(firstId, secondId, "The seller should have two distinct items");
            getSellerItems(requestAs(seller), seller.id())
                    .statusCode(200)
                    .body("$", instanceOf(List.class))
                    .body("id", containsInAnyOrder(firstId, secondId))
                    .body("id", not(hasItem(otherId)));
        }
    }

    @Test
    void shouldRejectSellerListWithNonNumericId() {
        SellerSession seller = createSeller();

        checkItemLookupRejected(getSellerItems(requestAs(seller), "abc"), 400);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "not-a-token")
    void shouldRejectItemCreationWithoutValidToken(String token) {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA стол " + seller.id());

        checkRejectedCreation(seller, item.name(), 401, () -> postItem(requestWithTestToken(token), item));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "not-a-token")
    void shouldRejectItemReadingWithoutValidToken(String token) {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA item " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            checkItemAccessDenied(getItem(requestWithTestToken(token), itemId), itemId, item.name());
        }
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "not-a-token")
    void shouldRejectSellerItemsReadingWithoutValidToken(String token) {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA item " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            checkItemAccessDenied(getSellerItems(requestWithTestToken(token), seller.id()), itemId, item.name());
        }
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "not-a-token")
    void shouldRejectStatisticsV1WithoutValidToken(String token) {
        checkStatisticsAccessDenied(1, token);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "not-a-token")
    void shouldRejectStatisticsV2WithoutValidToken(String token) {
        checkStatisticsAccessDenied(2, token);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "not-a-token")
    void shouldRejectItemDeletionWithoutValidToken(String token) {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA стол " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller, true)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            deleteItem(requestWithTestToken(token), itemId).statusCode(401);

            checkSavedItem(seller, itemId, item);
        }
    }

    @Test
    void shouldReturnSavedStatisticsInBothVersions() {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA statistics " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            checkSavedStatistics(getStatistics(requestAs(seller), 1, itemId), item.statistics(), true);
            checkSavedStatistics(getStatistics(requestAs(seller), 2, itemId), item.statistics(), false);
        }
    }

    @Test
    void shouldReturnNotFoundForMissingItemStatistics() {
        SellerSession seller = createSeller();
        String missingId = UUID.randomUUID().toString();

        assertAll("Statistics of an item that was never created",
                () -> checkMissingStatistics(getStatistics(requestAs(seller), 1, missingId)),
                () -> checkMissingStatistics(getStatistics(requestAs(seller), 2, missingId)));
    }

    @Test
    void shouldRejectStatisticsWithMalformedUuid() {
        SellerSession seller = createSeller();

        checkStatisticsRejected(getStatistics(requestAs(seller), 1, "not-a-uuid"), 400);
    }

    @Test
    void shouldDeleteOnlyRequestedItem() {
        SellerSession seller = createSeller();
        CreateItemRequest firstItem = testItem(seller, "QA first item " + seller.id());
        CreateItemRequest secondItem = testItem(seller, "QA second item " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller, true)) {
            String firstId = createTrackedItem(cleanup, seller, firstItem);
            String secondId = createTrackedItem(cleanup, seller, secondItem);
            checkSavedItem(seller, firstId, firstItem);
            getSellerItems(requestAs(seller), seller.id())
                    .statusCode(200)
                    .body("id", containsInAnyOrder(firstId, secondId));

            deleteItem(requestAs(seller), firstId).statusCode(200);
            checkItemLookupRejected(getItem(requestAs(seller), firstId), 404);
            cleanup.markDeleted(UUID.fromString(firstId));
            checkSavedItem(seller, secondId, secondItem);
            getSellerItems(requestAs(seller), seller.id())
                    .statusCode(200)
                    .body("id", equalTo(List.of(secondId)));
        }
    }

    @Disabled("API-21: statistics returned 200 after deletion; expected behavior is unspecified")
    @Test
    void shouldReturnNotFoundForDeletedItemStatistics() {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA deleted item statistics " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller, true)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            deleteItem(requestAs(seller), itemId).statusCode(200);
            getItem(requestAs(seller), itemId).statusCode(404);
            cleanup.markDeleted(UUID.fromString(itemId));

            assertAll("Statistics of a deleted item",
                    () -> assertEquals(404, getStatistics(requestAs(seller), 1, itemId)
                            .extract().statusCode(), "Statistics v1"),
                    () -> assertEquals(404, getStatistics(requestAs(seller), 2, itemId)
                            .extract().statusCode(), "Statistics v2"));
        }
    }

    @Test
    void shouldReturnNotFoundWhenDeletingMissingItems() {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA repeat delete " + seller.id());
        String missingId = UUID.randomUUID().toString();

        try (ItemCleanup cleanup = cleanupFor(seller, true)) {
            String deletedId = createTrackedItem(cleanup, seller, item);
            deleteItem(requestAs(seller), deletedId).statusCode(200);
            ValidatableResponse repeatedDeletion = deleteItem(requestAs(seller), deletedId);
            ValidatableResponse deletedItemLookup = getItem(requestAs(seller), deletedId);
            if (deletedItemLookup.extract().statusCode() == 404) {
                cleanup.markDeleted(UUID.fromString(deletedId));
            }

            assertAll("Deleting missing items",
                    () -> repeatedDeletion.statusCode(404),
                    () -> checkItemLookupRejected(deletedItemLookup, 404),
                    () -> deleteItem(requestAs(seller), missingId).statusCode(404),
                    () -> checkItemLookupRejected(getItem(requestAs(seller), missingId), 404));
        }
    }

    @Test
    void shouldRejectDeletionWithMalformedUuid() {
        SellerSession seller = createSeller();

        deleteItem(requestAs(seller), "not-a-uuid").statusCode(400);
    }

    @Test
    void shouldPreserveCyrillicAndQuotesInItemName() {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "Стол \"Дача\" " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            checkSavedItem(seller, itemId, item);
        }
    }

    @Test
    void shouldReturnSameItemOnRepeatedReads() {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA repeated reads " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            for (int attempt = 1; attempt <= 3; attempt++) {
                getItem(requestAs(seller), itemId)
                        .statusCode(200)
                        .body("$", instanceOf(List.class))
                        .body("size()", equalTo(1))
                        .body("[0].id", equalTo(itemId))
                        .body("[0].sellerId", equalTo(item.sellerID()))
                        .body("[0].name", equalTo(item.name()))
                        .body("[0].price", equalTo(item.price()));
            }
        }
    }

    private String createTrackedItem(ItemCleanup cleanup, SellerSession seller, CreateItemRequest item) {
        cleanup.findByNameOnClose(item.name());
        String itemId = createItem(seller, item);
        cleanup.trackCreatedItem(UUID.fromString(itemId));
        return itemId;
    }

    private ItemCleanup cleanupFor(SellerSession seller) {
        return cleanupFor(seller, false);
    }

    private ItemCleanup cleanupFor(SellerSession seller, boolean allowAlreadyDeleted) {
        return new ItemCleanup(seller.id(),
                () -> getSellerItems(requestAs(seller), seller.id()).statusCode(200).extract().jsonPath().getList("$"),
                itemId -> deleteItem(requestAs(seller), itemId.toString())
                        .statusCode(allowAlreadyDeleted ? anyOf(equalTo(200), equalTo(404)) : equalTo(200)));
    }

    private void checkRejectedCreation(SellerSession seller, String itemName, int expectedStatus,
                                       Supplier<ValidatableResponse> create) {
        try (ItemCleanup cleanup = cleanupFor(seller)) {
            cleanup.findByNameOnClose(itemName);
            ValidatableResponse creation = create.get();

            assertAll("Reject item creation",
                    () -> creation.statusCode(expectedStatus),
                    () -> assertEquals(List.of(), cleanup.readSellerItems(), "The seller's list should stay empty"));
        }
    }

    private void checkApi11Fixture(int status, List<String> firstList, List<String> secondList) {
        assertAll("Результат создания объявления",
                () -> assertEquals(400, status, "API-11 должен вернуть HTTP 400"),
                () -> assertFalse(firstList.contains(REJECTION_FIXTURE_ITEM_NAME), "Запись появилась в первом списке"),
                () -> assertFalse(secondList.contains(REJECTION_FIXTURE_ITEM_NAME), "Запись появилась во втором списке"));
    }

    private void checkMissingStatistics(ValidatableResponse response) {
        checkStatisticsRejected(response, 404);
    }

    private void checkStatisticsRejected(ValidatableResponse response, int expectedStatus) {
        response.statusCode(expectedStatus);
        ApiErrorBody.checkNoFields(response, STATISTICS_FIELDS);
    }

    private RequestSpecification requestWithTestToken(String token) {
        RequestSpecification specification = request();
        if (token != null) {
            specification.header("Authorization", "Bearer " + token);
        }
        return specification;
    }

    private void checkSavedStatistics(ValidatableResponse response, ItemStatistics expected, boolean requiresArray) {
        response.statusCode(200);
        Object body = response.extract().jsonPath().get("$");
        String prefix = "";

        if (requiresArray || body instanceof List<?>) {
            response.body("$", instanceOf(List.class))
                    .body("size()", equalTo(1));
            prefix = "[0].";
        }

        checkStatisticsCounters(response, prefix, expected);
    }

    private void checkSavedItem(SellerSession seller, String itemId, CreateItemRequest item) {
        ValidatableResponse response = getItem(requestAs(seller), itemId)
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(itemId))
                .body("[0].sellerId", equalTo(item.sellerID()))
                .body("[0].name", equalTo(item.name()))
                .body("[0].price", equalTo(item.price()));

        checkStatisticsCounters(response, "[0].statistics.", item.statistics());
    }

    private void checkStatisticsCounters(ValidatableResponse response, String prefix, ItemStatistics expected) {
        response.body(prefix + "likes", equalTo(expected.likes()))
                .body(prefix + "viewCount", equalTo(expected.viewCount()))
                .body(prefix + "contacts", equalTo(expected.contacts()));
    }

    private void checkItemLookupRejected(ValidatableResponse response, int expectedStatus) {
        response.statusCode(expectedStatus);
        ApiErrorBody.checkNoFields(response, ITEM_FIELDS);
    }

    private void checkItemAccessDenied(ValidatableResponse response, String itemId, String itemName) {
        response.statusCode(401)
                .body(not(anyOf(
                        containsString(itemId),
                        containsString(itemName)
                )));
        ApiErrorBody.checkNoFields(response, ITEM_FIELDS);
    }

    private void checkInvalidRegistration(Object body) {
        register(body)
                .statusCode(400)
                .body("id", nullValue())
                .body("username", nullValue());
    }

    private void checkStatisticsAccessDenied(int version, String token) {
        SellerSession seller = createSeller();
        CreateItemRequest item = testItem(seller, "QA стол " + seller.id());

        try (ItemCleanup cleanup = cleanupFor(seller)) {
            String itemId = createTrackedItem(cleanup, seller, item);
            checkStatisticsRejected(getStatistics(requestWithTestToken(token), version, itemId), 401);
        }
    }
}
