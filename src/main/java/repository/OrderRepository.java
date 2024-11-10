package repository;

import domain.order.Order;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderRepository {

    private Map<String,Integer> orderTable  =new LinkedHashMap<>();
    // 주문을 추가하는 메서드
    public void addOrder(Order order) {
        String orderName = order.getName();

        if(orderTable.containsKey(orderName )){
            orderTable.put(orderName , orderTable.get(orderName)+order.getOrderQuantity());
            return;
        }
        orderTable.put(order.getName(), order.getOrderQuantity());
    }

    // 주문 목록을 초기화 (새 주문 시 기존 목록을 비우기 위해 사용)
    public void clearOrders() {
        orderTable.clear();
    }

    public Map<String,Integer> getOrderTable() {
        return orderTable;
    }


}
