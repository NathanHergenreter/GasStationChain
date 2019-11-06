package gasChain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GasStation extends Store {

	@Cascade({CascadeType.PERSIST,CascadeType.REMOVE})
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gasStation")
	private List<GasStationInventory> inventory = new ArrayList<>();

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "sellLocation")
    private List<Sale> sales = new ArrayList<>();

    @Cascade({CascadeType.PERSIST})
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

	@OneToMany(mappedBy = "workplace")
    @Cascade({CascadeType.SAVE_UPDATE})
	private List<Cashier> cashiers = new ArrayList<>();

	protected GasStation() {
		super();
	}

	public GasStation(String location, String state, String region) {
		super(location, state, region);
	}

//    public GasStation(@NotNull double longitude, @NotNull double latitude, String name, GasStationInventory... inventoryItems) {
//        super(longitude, latitude, name);
//        for (GasStationInventory inventory : inventoryItems) inventory.setGasStation(this);
//        this.inventory = Stream.of(inventoryItems).collect(Collectors.toSet());
//    }

	public List<GasStationInventory> getInventory() {
		return inventory;
	}
	
	public GasStation setInventory(List<GasStationInventory> inventory) { this.inventory = inventory; return this; }
	
	public GasStation addInventory(GasStationInventory item) { inventory.add(item); return this; }

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
