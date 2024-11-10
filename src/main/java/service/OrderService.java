package service;


import domain.invertory.Inventory;
import domain.order.Order;
import java.util.Map;
import message.ExceptionMessage;
import message.OrderMessage;
import repository.InventoryRepository;
import repository.OrderRepository;
import view.InputView;

public class OrderService {
    private OrderRepository orderRepository;
    private InventoryRepository inventoryRepository;
    private InputView inputView;

    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository, InputView inputView) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.inputView = inputView;
    }

    public void makeOrderTable() {
        while (true) {
            String orderInput = inputView.getOrderInput();  // 사용자로부터 주문 입력 받기
            try {
                isValidOrderFormat(orderInput);
                orderRepository.clearOrders();
                String[] items = parseOrderInput(orderInput);
                createOrdersFromInput(items);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateOrder(String orderName, int orderQuantity) {
        isPositiveNumber(orderQuantity);
        validateInventoryAvailability(orderName, orderQuantity);
    }


    private void validateInventoryAvailability(String orderName, int orderQuantity) {
        Inventory inventory = inventoryRepository.findStockByName(orderName);
        if (inventory == null) {
            throw new IllegalArgumentException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage());
        }
        int totalStock = inventory.getGeneralStockQuantity() + inventory.getPromotionStockQuantity();
        if (totalStock < orderQuantity) {
            throw new IllegalArgumentException(ExceptionMessage.OUT_OF_STOCK.getMessage());
        }
    }


    // 정규식으로 입력 값 검증
    private boolean isValidOrderFormat(String orderInput) {
        String regex = OrderMessage.ORDER_INPUT_REGEX.getMessage();
        //"^\\[([A-Za-z가-힣]+-\\d+)\\](,\\[([A-Za-z가-힣]+-\\d+)\\])*$"
        if (!orderInput.matches(regex)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER_FORMAT.getMessage());
        }
        return true;
    }

    // 주문 입력을 ','로 분리하고 대괄호를 제거하여 배열로 반환
    private String[] parseOrderInput(String orderInput) {
        String[] items = orderInput.split(OrderMessage.ITEM_SEPARATOR.getMessage());  // ','로 구분하고 대괄호가 포함된 부분을 처리
        for (int i = 0; i < items.length; i++) {
            items[i] = items[i].replace("[", "").replace("]", ""); // 대괄호 제거
        }
        return items;
    }

    // 각 주문 항목을 처리하여 Order 객체를 생성하고 저장하는 메서드
    private void createOrdersFromInput(String[] items) {
        for (String item : items) {
            String[] parts = item.split("-");
            String orderName = parts[0].trim();
            int orderQuantity = Integer.parseInt(parts[1].trim());
            validateOrder(orderName, orderQuantity);
            Order order = new Order(orderName, orderQuantity);
            orderRepository.addOrder(order);
        }
    }

    private void isPositiveNumber(int orderQuantity) {
        if (orderQuantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PRODUCT_QUANTITY.getMessage(orderQuantity));
        }
    }


    public Map<String, Integer> getOrderRepository() {
        return orderRepository.getOrderTable();
    }
}