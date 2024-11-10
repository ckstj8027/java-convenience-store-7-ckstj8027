package dto;

public class SaleDto {
    private int generalSaleQuantity;
    private int promotionSaleQuantity;

    public SaleDto(int generalSaleQuantity, int promotionSaleQuantity) {
        this.generalSaleQuantity = generalSaleQuantity;
        this.promotionSaleQuantity = promotionSaleQuantity;
    }

    public int getGeneralSaleQuantity() {
        return generalSaleQuantity;
    }

    public int getPromotionSaleQuantity() {
        return promotionSaleQuantity;
    }
}
