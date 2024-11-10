package startegy;


import domain.invertory.Inventory;


public class ActivePromotionStrategy implements PromotionStrategy {
    private final int minApplicablePromotion;


    public ActivePromotionStrategy(int minApplicablePromotion) {
        this.minApplicablePromotion = minApplicablePromotion;

    }


    @Override
    public int calculatePromotionSaleQuantity(int orderQuantity, Inventory inventory) {

        int loopCount = orderQuantity / minApplicablePromotion;  //3
        int promotionSaleQuantity = loopCount * minApplicablePromotion; //9
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


    public int getMinApplicablePromotion() {
        return minApplicablePromotion;
    }


}

