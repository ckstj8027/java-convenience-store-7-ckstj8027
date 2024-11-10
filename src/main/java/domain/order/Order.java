package domain.order;

public class Order {

    private String Name;
    private int orderQuantity;

    public Order(String name, int orderQuantity) {
        Name = name;
        this.orderQuantity = orderQuantity;
    }

    public String getName() {
        return Name;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }
}
