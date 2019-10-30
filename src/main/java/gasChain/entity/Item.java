package gasChain.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<WarehouseInventory> inWarehouses = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<GasStationInventory> inGasStations = new HashSet<>();

    private String description;
    private String name;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public Set<WarehouseInventory> getInWarehouses() {
        return inWarehouses;
    }
    public Set<GasStationInventory> getInGasStations() {
        return inGasStations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
