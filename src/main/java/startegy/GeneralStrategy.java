package startegy;

import domain.invertory.Inventory;

public class GeneralStrategy implements PromotionStrategy {
    private final int minApplicablePromotion = 0;

    public int getMinApplicablePromotion() {
        return minApplicablePromotion;
    }


    @Override
    public int calculatePromotionSaleQuantity(int orderQuantity, Inventory inventory) {

        return 0;
    }


    @Override
    public int calculateGeneralSaleQuantity(int orderQuantity, int promotionSaleQuantity) {
        return orderQuantity - promotionSaleQuantity;
    }
}
