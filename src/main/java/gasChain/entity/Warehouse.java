package gasChain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Warehouse extends Store {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<WarehouseInventory> inventory = new ArrayList<>();

    protected Warehouse() {
        super();
    }

    public Warehouse(String location, String state, String region) {
        super(location, state, region);
    }

    public List<WarehouseInventory> getWarehouseInventory() {
        return inventory;
    }

    public Warehouse addInventory(WarehouseInventory item) {
        inventory.add(item);
        return this;
    }

    public Warehouse removeInventory(WarehouseInventory item) {
        String itemType = item.getItem().getName();

        for (int idx = 0; idx < inventory.size(); idx++) {
            WarehouseInventory curItem = inventory.get(idx);
            String curItemType = curItem.getItem().getName();
            if (curItemType.equals(itemType)) {
                inventory.remove(idx);
                break;
            }
        }
        return this;
    }

    public WarehouseInventory findWarehouseInventory(Item item) {
        for (WarehouseInventory inventoryItem : inventory) {
            if (inventoryItem.ofItem(item.getName())) {
                return inventoryItem;
            }
        }
        return null;
    }
}
