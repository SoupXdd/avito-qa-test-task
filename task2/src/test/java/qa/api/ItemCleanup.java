package qa.api;

import org.junit.jupiter.api.function.Executable;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertAll;

final class ItemCleanup implements AutoCloseable {

    private final int sellerId;
    private final Supplier<List<Map<String, Object>>> listItems;
    private final Consumer<UUID> deleteItem;
    private final Set<UUID> itemIds = new LinkedHashSet<>();
    private String itemNameToFind;

    ItemCleanup(int sellerId, Supplier<List<Map<String, Object>>> listItems, Consumer<UUID> deleteItem) {
        this.sellerId = sellerId;
        this.listItems = listItems;
        this.deleteItem = deleteItem;
    }

    void markDeleted(UUID itemId) {
        itemIds.remove(itemId);
    }

    void findByNameOnClose(String itemName) {
        itemNameToFind = itemName;
    }

    void trackCreatedItem(UUID itemId) {
        itemIds.add(itemId);
        itemNameToFind = null;
    }

    List<Map<String, Object>> readSellerItems() {
        List<Map<String, Object>> sellerItems = listItems.get();
        if (itemNameToFind != null) {
            for (Map<String, Object> item : sellerItems) {
                if (itemNameToFind.equals(item.get("name")) && Integer.valueOf(sellerId).equals(item.get("sellerId"))) {
                    itemIds.add(UUID.fromString((String) item.get("id")));
                }
            }
            itemNameToFind = null;
        }
        return sellerItems;
    }

    @Override
    public void close() {
        assertAll("Clean up test items",
                () -> {
                    if (itemNameToFind != null) {
                        readSellerItems();
                    }
                },
                () -> assertAll("Delete tracked test items", itemIds.stream()
                        .<Executable>map(itemId -> () -> deleteItem.accept(itemId))));
    }
}
