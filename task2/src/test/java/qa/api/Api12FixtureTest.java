package qa.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Api12FixtureTest {

    private static final int SELLER_ID = 101;
    private static final String ITEM_NAME = "QA стол";
    private static final ListReply EMPTY_LIST = new ListReply(200, List.of());
    private static final ListReply LIST_WITH_ITEM = new ListReply(200, List.of(ITEM_NAME));

    @Test
    void acceptsItemSavedForAuthenticatedSeller() {
        checkAccepted(200, new ItemReply(200, SELLER_ID, ITEM_NAME));
    }

    @Test
    void detectsWrongOwnerOrName() {
        assertAll(
                () -> assertThrows(AssertionError.class, () -> checkAccepted(200, new ItemReply(200, 202, ITEM_NAME))),
                () -> assertThrows(AssertionError.class,
                        () -> checkAccepted(200, new ItemReply(200, SELLER_ID, "Другое название"))));
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 401, 403})
    void acceptsRejectionWithoutSavedItem(int status) {
        checkRejected(status, EMPTY_LIST, EMPTY_LIST);
    }

    @Test
    void detectsSavedItemInEitherListAfterRejection() {
        assertAll(
                () -> assertThrows(AssertionError.class, () -> checkRejected(400, LIST_WITH_ITEM, EMPTY_LIST)),
                () -> assertThrows(AssertionError.class, () -> checkRejected(400, EMPTY_LIST, LIST_WITH_ITEM)));
    }

    @Test
    void rejectsServerErrors() {
        assertAll(
                () -> assertThrows(AssertionError.class,
                        () -> checkAccepted(500, new ItemReply(200, SELLER_ID, ITEM_NAME))),
                () -> assertThrows(AssertionError.class, () -> checkRejected(503, EMPTY_LIST, EMPTY_LIST)),
                () -> assertThrows(AssertionError.class,
                        () -> checkRejected(400, EMPTY_LIST, new ListReply(500, List.of()))));
    }

    private static void checkAccepted(int creationStatus, ItemReply item) {
        assertAll("API-12 accepted creation",
                () -> assertEquals(200, creationStatus, "Create response"),
                () -> assertEquals(200, item.status(), "Read response"),
                () -> assertEquals(SELLER_ID, item.sellerId(), "Authenticated seller must own the item"),
                () -> assertEquals(ITEM_NAME, item.name(), "Saved item name"));
    }

    private static void checkRejected(int creationStatus, ListReply sellerItems, ListReply otherItems) {
        assertAll("API-12 rejected creation",
                () -> assertTrue(List.of(400, 401, 403).contains(creationStatus),
                        () -> "Expected a validation or access error, received HTTP " + creationStatus),
                () -> checkItemAbsent(sellerItems),
                () -> checkItemAbsent(otherItems));
    }

    private static void checkItemAbsent(ListReply reply) {
        assertEquals(200, reply.status(), "Seller list response");
        assertFalse(reply.names().contains(ITEM_NAME), "Test item appeared after rejected creation");
    }

    private record ItemReply(int status, int sellerId, String name) {}

    private record ListReply(int status, List<String> names) {}
}
