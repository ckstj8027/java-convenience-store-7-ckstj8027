package service;

import domain.product.Product;
import domain.promotion.Promotion;
import domain.sale.Sale;
import java.util.Map;
import repository.ProductRepository;
import repository.PromotionRepository;
import repository.SalesRepository;
import view.InputView;
import view.ReceiptView;


public class ReceiptService {
    private int totalAmount;
    private int salesTotalQuantity;
    private int generalAmount;
    int totalFreeGiftAmount;
    int freeGiftQuantity;
    private final InputView inputView;
    private final ReceiptView receiptView;
    private final SalesRepository salesRepository;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public ReceiptService(InputView inputView, ReceiptView receiptView, SalesRepository salesRepository,
                          PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.inputView = inputView;
        this.receiptView = receiptView;
        this.salesRepository = salesRepository;
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }


    public void makeReceipt() throws IllegalArgumentException {
        initReceiptAmount();
        Map<String, Sale> salesTable = salesRepository.getAllSales();
        Map<String, Product> productTable = productRepository.getProductTable();
        Map<String, Promotion> promotionTable = promotionRepository.getPromotionTable();
        int membershipDiscountPercentage = getMembershipDiscountPercentage();

        makeHeaderContent(salesTable, productTable);
        makeMiddleContent(salesTable, productTable, promotionTable);
        makeTailContent(membershipDiscountPercentage, totalFreeGiftAmount);
    }


    private void initReceiptAmount() {
        totalAmount = 0;
        salesTotalQuantity = 0;
        generalAmount = 0;
        totalFreeGiftAmount = 0;
        freeGiftQuantity = 0;
    }


    private void makeHeaderContent(Map<String, Sale> salesTable, Map<String, Product> productTable) {
        receiptView.printStatReceipt();
        for (Sale sale : salesTable.values()) {
            String itemName = sale.getSaleItemName();
            int totalQuantity = sale.getGeneralProductSaleQuantity() + sale.getPromotionProductSaleQuantity();
            int itemPrice = getPrice(itemName, productTable);
            salesTotalQuantity += totalQuantity;
            int currentAmount = itemPrice * totalQuantity;
            totalAmount += currentAmount;
            generalAmount(sale, itemPrice);
            receiptView.printContent(itemName, totalQuantity, currentAmount);
        }
    }


    private void generalAmount(Sale sale, int itemPrice) {
        int generalProductSaleQuantity = sale.getGeneralProductSaleQuantity();
        int promotionProductSaleQuantity = sale.getPromotionProductSaleQuantity();
        if (generalProductSaleQuantity > 0 && promotionProductSaleQuantity == 0) {
            generalAmount += itemPrice * generalProductSaleQuantity;
        }
    }


    private void makeMiddleContent(Map<String, Sale> salesTable, Map<String, Product> productTable,
                                   Map<String, Promotion> promotionTable) {
        receiptView.printFreeGiftTitle();
        for (Sale sale : salesTable.values()) {
            String itemName = sale.getSaleItemName();
            String promotionName = getPromotionName(itemName, productTable);
            Promotion promotion = getPromotion(promotionTable, promotionName);
            freeGiftQuantity = getFreeGiftQuantity(sale, promotion);
            totalFreeGiftAmount += freeGiftQuantity * getPrice(itemName, productTable);
            receiptView.printFreeGift(itemName, freeGiftQuantity);
        }
    }


    private int getFreeGiftQuantity(Sale sale, Promotion promotion) {
        int minApplicablePromotion;
        int freeGiftQuantity = 0;
        if (promotion != null) {
            minApplicablePromotion = promotion.getGet() + promotion.getBuy();
            freeGiftQuantity = sale.getPromotionProductSaleQuantity() / minApplicablePromotion;
        }

        return freeGiftQuantity;
    }

    private Promotion getPromotion(Map<String, Promotion> promotionTable, String promotionName) {
        Promotion promotion = new Promotion();
        if (promotionName != null) {
            promotion = promotionTable.get(promotionName);
        }
        return promotion;
    }


    private void makeTailContent(int membershipDiscountPercentage, int totalFreeGiftAmount) {

        receiptView.printTotalAmount(salesTotalQuantity, totalAmount);
        int membershipDiscountAmount = getMembershipDiscountAmount(membershipDiscountPercentage, generalAmount);
        receiptView.printDiscountContent(totalFreeGiftAmount, membershipDiscountAmount);
        receiptView.printReceiptAmount(totalAmount, totalFreeGiftAmount, membershipDiscountAmount);
    }

    private int getMembershipDiscountPercentage() {

        int membershipDiscountPercentage = 0;
        if (inputView.isMembershipDiscount()) {
            membershipDiscountPercentage = 30;
        }
        return membershipDiscountPercentage;

    }

    private int getMembershipDiscountAmount(int membershipDiscountPercentage, int generalAmount) {
        int membershipDiscountAmount = (generalAmount * membershipDiscountPercentage) / 100;
        if (membershipDiscountAmount > 8000) {
            membershipDiscountAmount = 8000;
        }
        return membershipDiscountAmount;
    }


    private String getPromotionName(String name, Map<String, Product> productTable) {

        String result = "";

        Product product = productTable.get(name + "_promotion");
        ;
        if (product != null) {
            result = product.getPromotionName();
        }

        return result;

    }

    private int getPrice(String name, Map<String, Product> productTable) {
        Product product = productTable.get(name + "_promotion");

        if (product != null) {
            return product.getPrice();
        }
        product = productTable.get(name + "_general");
        return product.getPrice();

    }


}
