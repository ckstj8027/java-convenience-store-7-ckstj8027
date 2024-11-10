package inventoryInitializer;

import domain.invertory.Inventory;
import domain.product.Product;
import domain.promotion.Promotion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import message.BasicInfoMessage;
import message.ExceptionMessage;
import repository.InventoryRepository;
import repository.ProductRepository;
import repository.PromotionRepository;

public class BasicInfo {
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;
    private InventoryRepository inventoryRepository;
    private static final String FILE_PATH = BasicInfoMessage.FILE_PATH.getMessage();
    private static final String PRODUCTS_FILE = BasicInfoMessage.PRODUCTS_FILE.getMessage();
    private static final String PROMOTIONS_FILE = BasicInfoMessage.PROMOTIONS_FILE.getMessage();


    public BasicInfo(ProductRepository productRepository, PromotionRepository promotionRepository,
                     InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.inventoryRepository = inventoryRepository;
    }


    public void loadBasicInfo() {
        List<String> productLines = readFile(PRODUCTS_FILE);
        List<String> promotionLines = readFile(PROMOTIONS_FILE);

        parsePromotions(promotionLines);
        parseProducts(productLines);
    }


    private List<String> readFile(String fileName) {
        try {
            Path path = Paths.get(FILE_PATH + fileName);
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println(ExceptionMessage.FILE_READ_ERROR);
            return null;
        }
    }


    private void parsePromotions(List<String> promotionLines) {
        String firstLine = promotionLines.get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BasicInfoMessage.BASIC_DATETIME_FORMAT.getMessage());

        for (String promotionLine : promotionLines) {
            if (promotionLine.equals(firstLine)) {
                continue; // 첫 번째 라인은 건너뜁니다
            }
            Promotion promotion = createPromotion(promotionLine, formatter);
            promotionRepository.addPromotion(promotion);
        }
    }


    private Promotion createPromotion(String promotionLine, DateTimeFormatter formatter) {
        String[] splitLines = promotionLine.split(",");
        String name = splitLines[0];
        int buy = Integer.parseInt(splitLines[1]);
        int get = Integer.parseInt(splitLines[2]);
        LocalDate localStartDate = LocalDate.parse(splitLines[3], formatter);
        LocalDate localEndDate = LocalDate.parse(splitLines[4], formatter);
        LocalDateTime startDate = localStartDate.atStartOfDay();
        LocalDateTime endDate = localEndDate.atStartOfDay();
        return new Promotion(name, buy, get, startDate, endDate);
    }


    private void parseProducts(List<String> productLines) {
        String firstLine = productLines.get(0);

        for (String productLine : productLines) {
            if (productLine.equals(firstLine)) {
                continue; // 첫 번째 라인은 건너뜁니다
            }
            Product product = createProduct(productLine);
            int quantity = getQuantity(productLine);
            productRepository.addProduct(product, product.getPromotionName());
            updateStock(product, quantity);
        }
    }


    private int getQuantity(String productLine) {
        String[] splitLine = productLine.split(",");

        return Integer.parseInt(splitLine[2]);

    }


    private Product createProduct(String productLine) {
        String[] splitLine = productLine.split(",");
        String name = splitLine[0];
        int price = Integer.parseInt(splitLine[1]);
        String promotionName = splitLine[3];

        return new Product(name, price, promotionName);
    }


    private void updateStock(Product product, int quantity) {
        String productName = product.getName();
        String promotionName = product.getPromotionName();
        Inventory inventory = inventoryRepository.findStockByName(productName);
        if (inventory == null) {
            inventory = new Inventory(productName, 0, 0);
        }
        updatePromotionInventory(quantity, promotionName, inventory);
        updateGeneralInventory(quantity, promotionName, inventory);
        inventoryRepository.addStock(inventory);
    }


    private static void updateGeneralInventory(int quantity, String promotionName, Inventory inventory) {
        if (!promotionName.equals("null")) {

            inventory.updatePromotionProductQuantity(inventory.getPromotionStockQuantity() + quantity);
        }
    }


    private static void updatePromotionInventory(int quantity, String promotionName, Inventory inventory) {
        if (promotionName.equals("null")) {

            inventory.updateGeneralProductQuantity(inventory.getGeneralStockQuantity() + quantity);
        }
    }


}
