package domain.sale;

public class Sale {
    private String saleItemName;
    private int generalProductSaleQuantity; // 일반 제품  판매수량
    private int promotionProductSaleQuantity; // 프로모션 판매 수량

    public Sale(String saleItemName, int generalProductSaleQuantity, int promotionProductSaleQuantity) {
        this.saleItemName = saleItemName;
        this.generalProductSaleQuantity = generalProductSaleQuantity;
        this.promotionProductSaleQuantity = promotionProductSaleQuantity;
    }

    public String getSaleItemName() {
        return saleItemName;
    }

    public int getGeneralProductSaleQuantity() {
        return generalProductSaleQuantity;
    }

    public int getPromotionProductSaleQuantity() {
        return promotionProductSaleQuantity;
    }
}
