package handler;

import domain.invertory.Inventory;
import dto.SaleDto;
import startegy.GeneralStrategy;


public class GeneralHandler implements PromotionHandler {


    private final GeneralStrategy generalStrategy;


    public GeneralHandler(GeneralStrategy generalStrategy) {
        this.generalStrategy = generalStrategy;
    }


    @Override
    public SaleDto handle(String orderName, int orderQuantity, Inventory inventory) {
        // 일반적인 처리
        int promotionSaleQuantity = generalStrategy.calculatePromotionSaleQuantity(orderQuantity, inventory);
        int generalSaleQuantity = generalStrategy.calculateGeneralSaleQuantity(orderQuantity, promotionSaleQuantity);

        return new SaleDto(generalSaleQuantity, promotionSaleQuantity);

    }


}
