package gasChain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class GasStation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String location;
    private String state;
    private String region;

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

    //Default Constructor
    protected GasStation() {
    }

    public GasStation(String location, GasStationInventory... inventoryItems) {
        this.location = location;
        for (GasStationInventory inventory : inventoryItems) inventory.setGasStation(this);
        this.inventory = Stream.of(inventoryItems).collect(Collectors.toSet());
    }

    public GasStation(String location, String state, String region, GasStationInventory... inventoryItems) {
        this.location = location;
        this.state = state;
        this.region = region;
        for (GasStationInventory inventory : inventoryItems) inventory.setGasStation(this);
        this.inventory = Stream.of(inventoryItems).collect(Collectors.toSet());

    }


    // Stupid-ass getters and setters fuck Java
    // If you visually hate them or just hate the boilerplate in general look into projectlombok
    public String getLocation() {
        return location;
    }

    public String getState() {
        return state;
    }

    public String getRegion() {
        return region;
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
