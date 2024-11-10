package handler;

import domain.invertory.Inventory;
import dto.SaleDto;

public interface PromotionHandler {
    SaleDto handle(String orderName, int orderQuantity, Inventory inventory);
}