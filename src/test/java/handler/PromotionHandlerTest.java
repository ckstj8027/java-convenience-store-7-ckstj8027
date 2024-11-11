package handler;

import domain.invertory.Inventory;
import dto.SaleDto;
import inventoryInitializer.BasicInfo;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InventoryRepository;
import repository.ProductRepository;
import repository.PromotionRepository;
import java.io.ByteArrayInputStream;
import startegy.ActivePromotionStrategy;
import startegy.GeneralStrategy;
import startegy.InActivePromotionStrategy;
import startegy.PromotionStrategy;
import startegy.PromotionStrategyFactory;

public class PromotionHandlerTest {

    ProductRepository productRepository;
    PromotionRepository promotionRepository;
    InventoryRepository inventoryRepository;
    PromotionStrategyFactory promotionStrategyFactory;
    PromotionStrategy activePromotionStrategy;
    PromotionStrategy inActivePromotionStrategy;
    PromotionStrategy generalStrategy;
    BasicInfo basicInfo;

    @BeforeEach
    void md파일_초기_세팅_준비() {
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();
        inventoryRepository = new InventoryRepository();
        basicInfo = new BasicInfo(productRepository, promotionRepository, inventoryRepository);
        basicInfo.loadBasicInfo();
    }

    @BeforeEach
    void 각_전략_사전_준비() {
        promotionStrategyFactory = new PromotionStrategyFactory(promotionRepository);
        activePromotionStrategy = new ActivePromotionStrategy(3);
        inActivePromotionStrategy = new InActivePromotionStrategy(3);
        generalStrategy = new GeneralStrategy();

    }

    @Test
    void 프로모션_활성화_전략_Handler() {
        PromotionHandler promotionHandler = PromotionHandlerFactory.getHandler(activePromotionStrategy);
        Assertions.assertInstanceOf(ActivePromotionHandler.class, promotionHandler);
    }

    @Test
    void 프로모션_비활성화_전략_Handler() {
        PromotionHandler promotionHandler = PromotionHandlerFactory.getHandler(inActivePromotionStrategy);
        Assertions.assertInstanceOf(InActivePromotionHandler.class, promotionHandler);
    }


    @Test
    void 일반_전략_Handler() {
        PromotionHandler promotionHandler = PromotionHandlerFactory.getHandler(generalStrategy);
        Assertions.assertInstanceOf(GeneralHandler.class, promotionHandler);
    }

    
    @Test
    void 프로모션_비활성화_전략_Handler_handle() {
        // 미리 정의한 입력값 설정

        PromotionHandler promotionHandler = PromotionHandlerFactory.getHandler(inActivePromotionStrategy);
        Inventory inventory = inventoryRepository.findStockByName("콜라");
        SaleDto saleDto = promotionHandler.handle("콜라", 11, inventory);
        Assertions.assertEquals(11, saleDto.getGeneralSaleQuantity());
        Assertions.assertEquals(0, saleDto.getPromotionSaleQuantity());

    }


    @Test
    void 일반_전략_Handler_handle() {
        PromotionHandler promotionHandler = PromotionHandlerFactory.getHandler(generalStrategy);
        Inventory inventory = inventoryRepository.findStockByName("콜라");
        SaleDto saleDto = promotionHandler.handle("콜라", 11, inventory);
        Assertions.assertEquals(11, saleDto.getGeneralSaleQuantity());
        Assertions.assertEquals(0, saleDto.getPromotionSaleQuantity());
    }


}





