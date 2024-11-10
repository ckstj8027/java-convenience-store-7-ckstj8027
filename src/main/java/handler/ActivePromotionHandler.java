package handler;

import domain.invertory.Inventory;
import dto.SaleDto;
import startegy.ActivePromotionStrategy;
import view.InputView;

public class ActivePromotionHandler implements PromotionHandler {

    private final ActivePromotionStrategy promotionStrategy;
    private InputView inputView;


    public ActivePromotionHandler(ActivePromotionStrategy promotionStrategy, InputView inputView) {
        this.promotionStrategy = promotionStrategy;
        this.inputView = inputView;
    }


    @Override
    public SaleDto handle(String orderName, int orderQuantity, Inventory inventory) throws IllegalArgumentException {
        int promotionSaleQuantity = promotionStrategy.calculatePromotionSaleQuantity(orderQuantity, inventory);
        int generalSaleQuantity = promotionStrategy.calculateGeneralSaleQuantity(orderQuantity, promotionSaleQuantity);
        // 추가 증정 상품 처리
        SaleDto updatedSaleDto = handleAdditionalGift(inventory, orderQuantity, promotionSaleQuantity, orderName);
        promotionSaleQuantity = updatedSaleDto.getPromotionSaleQuantity();
        generalSaleQuantity = updatedSaleDto.getGeneralSaleQuantity();
        // 손해보는 구매 처리
        updatedSaleDto = handleLossPurchase(generalSaleQuantity, promotionSaleQuantity, orderName);
        generalSaleQuantity = updatedSaleDto.getGeneralSaleQuantity();
        return new SaleDto(generalSaleQuantity, promotionSaleQuantity);
    }


    private SaleDto handleAdditionalGift(Inventory inventory, int orderQuantity, int promotionSaleQuantity,
                                         String orderName) {
        // 추가 증정 상품이 필요할 때 처리
        if (checkMoreGetItem(inventory.getPromotionStockQuantity(), orderQuantity, promotionSaleQuantity)) {
            if (inputView.getMoreOneView(orderName)) {
                return new SaleDto(0, orderQuantity + 1); // 추가 증정 상품 +1, 일반 상품은 0으로 설정
            }
        }
        return new SaleDto(promotionSaleQuantity, orderQuantity); // 기존 판매량 반환
    }


    private SaleDto handleLossPurchase(int generalSaleQuantity, int promotionSaleQuantity, String orderName) {

        if (purchaseWithLoss(generalSaleQuantity, promotionSaleQuantity)) {
            if (!inputView.applyPartialPromotion(orderName, generalSaleQuantity)) {
                generalSaleQuantity = 0;
            }
        }
        return new SaleDto(generalSaleQuantity, promotionSaleQuantity);
    }


    public boolean checkMoreGetItem(int promotionStockQuantity, int orderQuantity, int promotionSaleQuantity) {
        boolean result = false;
        int generalAddedQuantity = orderQuantity - promotionSaleQuantity;
        int minApplicablePromotion = promotionStrategy.getMinApplicablePromotion();
        if ((generalAddedQuantity == (minApplicablePromotion - 1)) && orderQuantity < promotionStockQuantity) {
            result = true;

        }
        return result;
    }


    // 손해보면서 구매할지 여부를 확인하는 메서드
    public boolean purchaseWithLoss(int generalSaleQuantity, int promotionSaleQuantity) {
        boolean result = false;

        if (promotionSaleQuantity != 0 && generalSaleQuantity != 0) {
            result = true;
        }
        return result;
    }


}
