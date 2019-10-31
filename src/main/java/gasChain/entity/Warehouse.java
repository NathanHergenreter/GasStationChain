package gasChain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Warehouse extends Store {

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<WarehouseInventory> inventory;

    protected Warehouse() {
        super();
    }

    public Warehouse(String location, String state, String region, WarehouseInventory... inventoryItems) {
        super(location, state, region);
        for (WarehouseInventory inventory : inventoryItems) inventory.setWarehouse(this);
        this.inventory = Stream.of(inventoryItems).collect(Collectors.toSet());
    }
    
    public Warehouse addInventory(WarehouseInventory item) { inventory.add(item); return this; }
}
