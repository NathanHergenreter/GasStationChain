package gasChain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class GasStation extends Store {

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = "gasStation")
	private Set<GasStationInventory> inventory;

//    @Cascade({CascadeType.PERSIST})
	@OneToMany(mappedBy = "sellLocation")
	private List<Sale> sales = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "manager_id", referencedColumnName = "id")
	private Manager manager;

	@Cascade({ CascadeType.PERSIST })
	@OneToMany(mappedBy = "workplace")
	private List<Cashier> cashiers = new ArrayList<>();

	private String location;
	private String state;
	private String region;

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

	public String getLocation() {
		return location;
	}

	public String getState() {
		return state;
	}

	public String getRegion() {
		return region;
	}

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
