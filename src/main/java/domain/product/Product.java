package domain.product;


public class Product {
    private String name;           // 제품 이름
    private int price;             // 제품 가격
    private String promotionName;  // 프로모션 이름 (프로모션이 없을 경우 null)

    // 생성자
    public Product(String name, int price, String promotionName) {
        this.name = name;
        this.price = price;
        this.promotionName = promotionName;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getPromotionName() {
        return promotionName;
    }


}
