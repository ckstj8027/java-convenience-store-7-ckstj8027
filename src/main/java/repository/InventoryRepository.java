package repository;



import java.util.*;
import domain.invertory.Inventory;

public class InventoryRepository {
    private Map<String, Inventory> inventoryTable = new LinkedHashMap<>();

    public void addStock(Inventory inventory) {
        inventoryTable.put(inventory.getInventoryName(), inventory);
    }

    public Map<String, Inventory> getInventoryTable() {
        return inventoryTable;
    }

    public Inventory findStockByName(String name) {
        return inventoryTable.get(name);
    }
}
