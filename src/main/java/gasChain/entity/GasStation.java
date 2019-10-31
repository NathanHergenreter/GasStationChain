package gasChain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "gas_stations")
public class GasStation extends Store {

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "gasStation")
    private Set<GasStationInventory> inventory;

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "sellLocation")
    private List<Sale> sales = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "workplace")
    private List<Cashier> cashiers = new ArrayList<>();

    protected GasStation() {
        super();
    }
    
    public GasStation(String location, String state, String region) { super(location, state, region); }

//    public GasStation(@NotNull double longitude, @NotNull double latitude, String name, GasStationInventory... inventoryItems) {
//        super(longitude, latitude, name);
//        for (GasStationInventory inventory : inventoryItems) inventory.setGasStation(this);
//        this.inventory = Stream.of(inventoryItems).collect(Collectors.toSet());
//    }
    

    public Set<GasStationInventory> getInventory() {
        return inventory;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public GasStation addSale(Sale sale) {
        sales.add(sale);
        return this;
    }

    public Manager getManager() {
        return manager;
    }

    public GasStation setManager(Manager manager) {
        this.manager = manager;
        return this;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public GasStation addCashier(Cashier cashier) {
        cashiers.add(cashier);
        return this;
    }

}
