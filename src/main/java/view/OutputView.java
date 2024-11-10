package view;

import domain.invertory.Inventory;
import domain.product.Product;
import java.util.Map;
import message.OutputMessage;

public class OutputView {


    public void displayProducts(Map<String, Product> productTable, Map<String, Inventory> inventoryTable) {

        System.out.println(OutputMessage.GREETING.getMessage());
        System.out.println(OutputMessage.CURRENT_PRODUCTS.getMessage());
        System.out.println();
        currentProductView(productTable, inventoryTable);
    }


    private void currentProductView(Map<String, Product> allProducts, Map<String, Inventory> allStocks) {
        for (Inventory inventory : allStocks.values()) {
            String stockItemName = inventory.getInventoryName();
            int price = getPrice(stockItemName, allProducts);
            Product promotionItem = getPromotionItem(allProducts, stockItemName);
            if (promotionItem != null) {
                printQuantityDisplay(inventory.getPromotionStockQuantity(), stockItemName, price,
                        promotionItem.getPromotionName());
            }
            printQuantityDisplay(inventory.getGeneralStockQuantity(), stockItemName, price, " ");

        }
    }


    private void printQuantityDisplay(int quantity, String stockItemName, int price, String promotionName) {
        // 수량이 0이면 재고 없음, 그렇지 않으면 수량과 함께 출력

        System.out.println(getInStockDisplay(stockItemName, price, quantity, promotionName));

    }


    private String getInStockDisplay(String stockItemName, int price, int quantity, String promotionName) {

        String promotionDisplay = ""; // 프로모션 디스플레이 초기화

        if (promotionName != null) {
            promotionDisplay = promotionName; // 프로모션이 있는 경우에만 값 설정
        }

        // 재고가 0일 경우 "재고 없음" 메시지 반환
        if (quantity == 0) {
            return OutputMessage.SOLDOUT_DISPLAY_FORMAT.getMessage(stockItemName, price, promotionDisplay);
        }

        // 재고가 있을 경우 수량과 프로모션명 함께 반환
        return OutputMessage.PRODUCT_DISPLAY_FORMAT.getMessage(stockItemName, price, quantity, promotionDisplay);

    }

    private Product getPromotionItem(Map<String, Product> allProducts, String name) {
        if (allProducts.containsKey(name + "_promotion")) {
            return allProducts.get(name + "_promotion");
        }
        return null;
    }


    private int getPrice(String name, Map<String, Product> productTable) {
        Product product = productTable.get(name + "_promotion");

        if (product != null) {
            return product.getPrice();
        }
        product = productTable.get(name + "_general");
        return product.getPrice();

    }
}
