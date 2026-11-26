package qa.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemClientTest {

    @Test
    void shouldExtractUuidRegardlessOfStatusWording() {
        String id = "123e4567-e89b-12d3-a456-426614174000";

        assertEquals(id, ItemClient.itemIdFromStatus("Создано: " + id + " (объявление)"));
    }

    @Test
    void shouldRejectMissingOrAmbiguousUuid() {
        assertThrows(AssertionError.class, () -> ItemClient.itemIdFromStatus("Объявление создано"));
        assertThrows(AssertionError.class, () -> ItemClient.itemIdFromStatus(
                "123e4567-e89b-12d3-a456-426614174000, 123e4567-e89b-12d3-a456-426614174001"));
    }
}
