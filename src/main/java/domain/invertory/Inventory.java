package domain.invertory;



public class Inventory {
    private String inventoryName;
    private int generalStockQuantity; // 일반 제품 수량
    private int promotionStockQuantity; // 프로모션 제품 수량

    public Inventory(String inventoryName, int generalStockQuantity, int promotionStockQuantity) {
        this.inventoryName = inventoryName;
        this.generalStockQuantity = generalStockQuantity;
        this.promotionStockQuantity = promotionStockQuantity;
    }

    public void updateGeneralProductQuantity(int quantity) {
        this.generalStockQuantity =quantity;
    }

    public void updatePromotionProductQuantity(int quantity) {
        this.promotionStockQuantity =quantity;
    }


    public String getInventoryName() {
        return inventoryName;
    }

    public int getGeneralStockQuantity() {
        return generalStockQuantity;
    }

    public int getPromotionStockQuantity() {
        return promotionStockQuantity;
    }

    @Override
    public String toString() {
        return inventoryName + " - 일반 수량: " + generalStockQuantity +
                ", 프로모션 수량: " + promotionStockQuantity;

    }
}
