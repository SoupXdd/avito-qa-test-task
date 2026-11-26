package qa.api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemCleanupTest {

    @Test
    void shouldFindOnlyTheMatchingItemOfTheTestSellerAfterAnAmbiguousCreate() {
        UUID expected = UUID.randomUUID();
        UUID otherOwner = UUID.randomUUID();
        UUID otherItem = UUID.randomUUID();
        List<UUID> deleted = new ArrayList<>();
        List<Map<String, Object>> items = List.of(
                Map.of("id", expected.toString(), "sellerId", 101, "name", "QA table"),
                Map.of("id", otherOwner.toString(), "sellerId", 202, "name", "QA table"),
                Map.of("id", otherItem.toString(), "sellerId", 101, "name", "Another item"));

        try (ItemCleanup cleanup = new ItemCleanup(101, () -> items, deleted::add)) {
            cleanup.findByNameOnClose("QA table");
        }

        assertEquals(List.of(expected), deleted);
    }

    @Test
    void shouldRemoveTheFallbackAfterRecordingTheCreatedUuid() {
        UUID itemId = UUID.randomUUID();
        List<UUID> deleted = new ArrayList<>();

        try (ItemCleanup cleanup = new ItemCleanup(101,
                () -> { throw new AssertionError("The seller list should not be read when the UUID is known"); },
                deleted::add)) {
            cleanup.findByNameOnClose("QA table");
            cleanup.trackCreatedItem(itemId);
        }

        assertEquals(List.of(itemId), deleted);
    }

    @Test
    void shouldKeepTheOriginalFailureAndAttemptEveryDeletion() {
        UUID first = UUID.randomUUID();
        UUID second = UUID.randomUUID();
        List<UUID> attempted = new ArrayList<>();
        AssertionError original = new AssertionError("Item assertion failed");

        AssertionError reported = assertThrows(AssertionError.class, () -> {
            try (ItemCleanup cleanup = new ItemCleanup(101, List::of, id -> {
                attempted.add(id);
                if (id.equals(first)) {
                    throw new AssertionError("First deletion failed");
                }
            })) {
                cleanup.trackCreatedItem(first);
                cleanup.trackCreatedItem(second);
                throw original;
            }
        });

        assertSame(original, reported);
        assertEquals(1, reported.getSuppressed().length);
        assertEquals(List.of(first, second), attempted);
    }
}
