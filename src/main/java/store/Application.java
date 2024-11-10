package store;



import controller.Controller;
import inventoryInitializer.BasicInfo;
import repository.OrderRepository;
import service.OrderService;
import repository.ProductRepository;
import repository.PromotionRepository;
import repository.SalesRepository;
import repository.InventoryRepository;
import service.ConvenienceService;
import service.ReceiptService;
import startegy.PromotionStrategyFactory;
import view.InputView;
import view.OutputView;
import view.ReceiptView;

public class Application {

    public static void main(String[] args) {

        Controller controller = creatController();

        controller.play();


    }

    private static Controller creatController() {
        ProductRepository productRepository = new ProductRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        InventoryRepository inventoryRepository = new InventoryRepository();
        OrderRepository orderRepository = new OrderRepository();
        SalesRepository salesRepository = new SalesRepository();
        InputView inputView=new InputView();
        OutputView outputView=new OutputView();
        ReceiptView receiptView = new ReceiptView();
        PromotionStrategyFactory promotionStrategyFactory = new PromotionStrategyFactory(promotionRepository );
        OrderService orderService = new OrderService(orderRepository,inventoryRepository,inputView);
        ReceiptService receiptService=new ReceiptService(inputView,receiptView,salesRepository,promotionRepository,productRepository);
        ConvenienceService convenienceService = new ConvenienceService(orderService,receiptService ,promotionRepository, productRepository,
                inventoryRepository,promotionStrategyFactory,salesRepository,inputView,outputView);
        BasicInfo basicInfo = new BasicInfo(productRepository ,promotionRepository, inventoryRepository);
        return new Controller(basicInfo,convenienceService);
    }


}
