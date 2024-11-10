package handler;

import startegy.ActivePromotionStrategy;
import startegy.GeneralStrategy;
import startegy.InActivePromotionStrategy;
import startegy.PromotionStrategy;
import view.InputView;

public class PromotionHandlerFactory {

    
    public static PromotionHandler getHandler(PromotionStrategy promotionStrategy) {

        if (promotionStrategy instanceof ActivePromotionStrategy) {
            return new ActivePromotionHandler((ActivePromotionStrategy) promotionStrategy, new InputView());
        }
        if (promotionStrategy instanceof InActivePromotionStrategy) {
            return new InActivePromotionHandler((InActivePromotionStrategy) promotionStrategy);
        }
        return new GeneralHandler((GeneralStrategy) promotionStrategy);
    }


}