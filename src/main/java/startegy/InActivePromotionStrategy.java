package startegy;

import domain.invertory.Inventory;

public class InActivePromotionStrategy implements PromotionStrategy {


    private final int minApplicablePromotion;


    public InActivePromotionStrategy(int minApplicablePromotion) {
        this.minApplicablePromotion = minApplicablePromotion;
    }


    public int getMinApplicablePromotion() {
        return minApplicablePromotion;
    }


    @Override
    public int calculatePromotionSaleQuantity(int orderQuantity, Inventory inventory) {

        int loopCount = orderQuantity / minApplicablePromotion;  //1
        int promotionSaleQuantity = loopCount * minApplicablePromotion; //3
        int promotionStockQuantity = inventory.getPromotionStockQuantity(); // 재고뽑은

        if (promotionSaleQuantity > promotionStockQuantity) {
            promotionSaleQuantity = (promotionStockQuantity / minApplicablePromotion) * minApplicablePromotion;
        }
        return promotionSaleQuantity;
    }


    @Override
    public int calculateGeneralSaleQuantity(int orderQuantity, int promotionSaleQuantity) {
        return orderQuantity - promotionSaleQuantity;
    }

}

