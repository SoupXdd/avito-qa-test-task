package qa.api;

record CreateItemRequest(int sellerID, String name, int price, ItemStatistics statistics) {

    static CreateItemRequest testItem(SellerSession seller, String name) {
        return new CreateItemRequest(seller.id(), name, 9900, new ItemStatistics(21, 11, 43));
    }
}
