package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Set<WarehouseInventory> inventory;

    public Warehouse(String name, String address, @Pattern(regexp = "(^$|[0-9]{10})") String phoneNumber, WarehouseInventory... inventoryItems) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        for(WarehouseInventory inventory: inventoryItems) inventory.setWarehouse(this);
        this.inventory = Stream.of(inventoryItems).collect(Collectors.toSet());
    }
}
