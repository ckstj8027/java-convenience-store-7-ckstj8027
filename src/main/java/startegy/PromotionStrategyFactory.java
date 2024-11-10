package startegy;

import camp.nextstep.edu.missionutils.DateTimes;
import domain.promotion.Promotion;
import java.time.LocalDateTime;
import repository.PromotionRepository;


public class PromotionStrategyFactory {


    private PromotionRepository promotionRepository;


    public PromotionStrategyFactory(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    
    public PromotionStrategy getPromotionStrategy(String promotionName) {
        if (promotionName != null) {
            int minApplicable = getPromotionMinApplicable(promotionName);
            if (isWithinPromotionPeriod(promotionName)) {
                return new ActivePromotionStrategy(minApplicable);
            }
            return new InActivePromotionStrategy(minApplicable);
        }
        return new GeneralStrategy(); // 프로모션이 없는 기본 전략
    }


    private boolean isWithinPromotionPeriod(String promotionName) {
        Promotion promotion = new Promotion();
        promotion = promotionRepository.findPromotionByPromotionName(promotionName);
        LocalDateTime startDate = promotion.getStart_date();
        LocalDateTime endDate = promotion.getEnd_date();
        LocalDateTime now = DateTimes.now();

        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            return true;
        }
        return false;
    }


    private int getPromotionMinApplicable(String promotionName) {
        int result = 0;
        Promotion promotion = promotionRepository.findPromotionByPromotionName(promotionName);
        if (promotion != null) {
            result = promotion.getGet() + promotion.getBuy();
        }
        return result;
    }

}
