package repository;


import java.util.LinkedHashMap;
import java.util.Map;

import domain.promotion.Promotion;

public class PromotionRepository {


    private final Map<String, Promotion> promotionTable =new LinkedHashMap<>();


    public void addPromotion(Promotion promotion){

        promotionTable.put(promotion.getName(), promotion);
    }


    public Promotion findPromotionByPromotionName(String promotionName){

        return promotionTable.get(promotionName);
    }


    public Map<String, Promotion> getPromotionTable() {
        return promotionTable;
    }
}
