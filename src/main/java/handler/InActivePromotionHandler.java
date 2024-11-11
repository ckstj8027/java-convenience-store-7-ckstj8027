package handler;


import domain.invertory.Inventory;
import dto.SaleDto;
import startegy.InActivePromotionStrategy;

public class InActivePromotionHandler implements PromotionHandler {


    private final InActivePromotionStrategy promotionStrategy;


    public InActivePromotionHandler(InActivePromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;

    }


    @Override
    public SaleDto handle(String orderName, int orderQuantity, Inventory inventory) {
        // 비활성화된 프로모션 처리
        int promotionSaleQuantity = promotionStrategy.calculatePromotionSaleQuantity(orderQuantity, inventory);
        int generalSaleQuantity = promotionStrategy.calculateGeneralSaleQuantity(orderQuantity, promotionSaleQuantity);
        generalSaleQuantity = generalSaleQuantity + promotionSaleQuantity;
        promotionSaleQuantity = 0;

        return new SaleDto(generalSaleQuantity, promotionSaleQuantity);

    }
}