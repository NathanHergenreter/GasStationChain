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

	private String name;
	private String location;
	private String state;
	private String region;
	
	protected Warehouse() {
		super();
	}

	public Warehouse(String location, String state, String region) {
		super(location, state, region);
	}

	public Warehouse(@NotNull double longitude, @NotNull double latitude, String name,
			@Pattern(regexp = "(^$|[0-9]{10})") String phoneNumber, WarehouseInventory... inventoryItems) {
		super();
		this.phoneNumber = phoneNumber;
		for (WarehouseInventory inventory : inventoryItems)
			inventory.setWarehouse(this);
		this.inventory = Stream.of(inventoryItems).collect(Collectors.toSet());
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getState() {
		return state;
	}
	
	public String getRegion() {
		return region;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public Set<WarehouseInventory> getWarehouseInventory() {
		return inventory;
	}
	
	public Warehouse addInventory(WarehouseInventory item) {
		inventory.add(item);
		return this;
	}
}
