package service;

import domain.invertory.Inventory;
import domain.product.Product;
import domain.sale.Sale;
import dto.SaleDto;
import handler.PromotionHandler;
import handler.PromotionHandlerFactory;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import message.ExceptionMessage;
import repository.InventoryRepository;
import repository.ProductRepository;
import repository.PromotionRepository;
import repository.SalesRepository;
import startegy.PromotionStrategy;
import startegy.PromotionStrategyFactory;
import view.InputView;
import view.OutputView;

public class ConvenienceService {

    private OrderService orderService;
    private ReceiptService receiptService;
    private PromotionRepository promotionRepository;
    private ProductRepository productRepository;
    private InventoryRepository inventoryRepository;
    private PromotionStrategyFactory promotionStrategyFactory;
    private SalesRepository salesRepository;
    private InputView inputView;
    private OutputView outputView;

    public ConvenienceService(OrderService orderService, ReceiptService receiptService,
                              PromotionRepository promotionRepository, ProductRepository productRepository,
                              InventoryRepository inventoryRepository,
                              PromotionStrategyFactory promotionStrategyFactory,
                              SalesRepository salesRepository, InputView inputView, OutputView outputView) {
        this.orderService = orderService;
        this.receiptService = receiptService;
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.promotionStrategyFactory = promotionStrategyFactory;
        this.salesRepository = salesRepository;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void displaySalesProductTable() {

        Map<String, Product> productTable = productRepository.getProductTable();
        Map<String, Inventory> inventoryTable = inventoryRepository.getInventoryTable();

        outputView.displayProducts(productTable, inventoryTable);

    }


    public void processOrder() {
        orderService.makeOrderTable();
    }


    public void makeSaleInfo() throws IllegalArgumentException {
        Map<String, Integer> orders = orderService.getOrderRepository();
        Set<Entry<String, Integer>> orderTable = orders.entrySet();

        for (Entry<String, Integer> order : orderTable) {
            String orderName = order.getKey();
            int orderQuantity = order.getValue();
            Inventory inventory = inventoryRepository.findStockByName(orderName);
            validateInventoryAvailability(inventory, orderQuantity);

            SaleDto saleDto = getSaleDto(orderName, orderQuantity, inventory);
            saveSale(orderName, saleDto.getGeneralSaleQuantity(), saleDto.getPromotionSaleQuantity());
        }
    }


    private SaleDto getSaleDto(String orderName, int orderQuantity, Inventory inventory) {
        String promotionName = getPromotionName(orderName);

        PromotionStrategy promotionStrategy = promotionStrategyFactory.getPromotionStrategy(promotionName);
        PromotionHandler promotionHandler = PromotionHandlerFactory.getHandler(promotionStrategy);
        return promotionHandler.handle(orderName, orderQuantity, inventory);
    }


    public boolean checkContinueShopping() {
        return inputView.continueView();
    }


    private void validateInventoryAvailability(Inventory inventory, int orderQuantity) {
        if (inventory == null) {
            throw new IllegalArgumentException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage());
        }
        int totalStock = inventory.getGeneralStockQuantity() + inventory.getPromotionStockQuantity();
        if (totalStock < orderQuantity) {
            throw new IllegalArgumentException(ExceptionMessage.OUT_OF_STOCK.getMessage());
        }
    }


    private String getPromotionName(String orderName) {
        Map<String, Product> allProducts = productRepository.getProductTable();

        for (Product product : allProducts.values()) {
            if (isOrderItemHavePromotion(orderName, product)) {
                return product.getPromotionName();
            }
        }
        return null;
    }


    private static boolean isOrderItemHavePromotion(String orderName, Product product) {
        return !product.getPromotionName().equals("null") && orderName.equals(product.getName());
    }


    private void saveSale(String orderName, int generalSaleQuantity, int promotionSaleQuantity) {
        Sale sale = new Sale(orderName, generalSaleQuantity, promotionSaleQuantity);

        salesRepository.addSale(sale);
    }


    public void makeReceipt() {

        receiptService.makeReceipt();
    }


    public void adjustInventory() {
        Map<String, Sale> salesTable = salesRepository.getAllSales();

        for (Sale sale : salesTable.values()) {
            adjustInventoryForSale(sale);
        }
    }


    private void adjustInventoryForSale(Sale sale) {
        String saleItemName = sale.getSaleItemName();
        Inventory inventory = inventoryRepository.findStockByName(saleItemName);
        int totalSaleQuantity = sale.getGeneralProductSaleQuantity() + sale.getPromotionProductSaleQuantity();
        int generalStockQuantity = inventory.getGeneralStockQuantity();
        int promotionStockQuantity = inventory.getPromotionStockQuantity();
        int[] remainingQuantities = calculateRemainingQuantities(generalStockQuantity, promotionStockQuantity,
                totalSaleQuantity);

        inventory.updateGeneralProductQuantity(remainingQuantities[0]);
        inventory.updatePromotionProductQuantity(remainingQuantities[1]);
    }


    private int[] calculateRemainingQuantities(int generalStockQuantity, int promotionStockQuantity,
                                               int totalSaleQuantity) {
        int remainPromotionStockQuantity = promotionStockQuantity - totalSaleQuantity;
        int remainGeneralStockQuantity = generalStockQuantity;

        if (remainPromotionStockQuantity < 0) { // 토탈판매수량 >프로모션 재고
            remainGeneralStockQuantity += remainPromotionStockQuantity;  // 초과된 판매량을 일반 재고에서 차감
            remainPromotionStockQuantity = 0;
        }

        return new int[]{remainGeneralStockQuantity, remainPromotionStockQuantity};
    }


    public void initSale() {
        salesRepository.initSale();
    }


}
