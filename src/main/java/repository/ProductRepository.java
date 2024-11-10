package repository;

import domain.product.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductRepository {
    private Map<String, Product> productTable = new LinkedHashMap<>();

    // Product 객체 추가 메서드
    public void addProduct(Product product,String promotionName ) {
        if(promotionName.equals("null")){
            productTable.put(product.getName()+"_"+"general", product);
            return;
        }

        productTable.put(product.getName()+"_"+ "promotion", product);
    }

    // 모든 Product 객체 조회 메서드
    public Map<String, Product> getProductTable() {
        return productTable;
    }

    // 제품 이름으로 Product 객체 검색 메서드
    public Product findProductByName(String name) {
        return productTable.get(name);
    }
}
