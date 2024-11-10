package startegy;

import domain.invertory.Inventory;


public interface PromotionStrategy {

    int calculatePromotionSaleQuantity(int orderQuantity, Inventory inventory);

    int calculateGeneralSaleQuantity(int orderQuantity, int promotionSaleQuantity);

}