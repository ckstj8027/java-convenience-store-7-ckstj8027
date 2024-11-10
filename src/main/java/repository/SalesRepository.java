package repository;

import java.util.LinkedHashMap;
import java.util.Map;
import domain.sale.Sale;


public class SalesRepository {

    private Map<String, Sale> salesTable = new LinkedHashMap<>();

    public void addSale(Sale sale) {

        salesTable.put(sale.getSaleItemName(),sale);
    }

    public Map<String,Sale> getAllSales() {

        return salesTable;
    }

    public Sale findStockByName(String name) {
        return salesTable.get(name);
    }

    public void initSale(){
        salesTable.clear();
    }


}
