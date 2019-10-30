package gasChain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Warehouse extends Store {

    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<WarehouseInventory> inventory;

    protected Warehouse() {
        super();
    }

    public Warehouse(@NotNull double longitude, @NotNull double latitude, String name, @Pattern(regexp = "(^$|[0-9]{10})") String phoneNumber, WarehouseInventory... inventoryItems) {
        super(longitude, latitude, name);
        this.phoneNumber = phoneNumber;
        for (WarehouseInventory inventory : inventoryItems) inventory.setWarehouse(this);
        this.inventory = Stream.of(inventoryItems).collect(Collectors.toSet());
    }
}
