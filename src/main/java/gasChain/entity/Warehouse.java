package gasChain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Warehouse extends Store {

	@Pattern(regexp = "(^$|[0-9]{10})")
	private String phoneNumber;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "warehouse", cascade = CascadeType.ALL)
	private List<WarehouseInventory> inventory = new ArrayList<>();
	
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
		this.inventory = Stream.of(inventoryItems).collect(Collectors.toList());
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public List<WarehouseInventory> getWarehouseInventory() {
		return inventory;
	}
	
	public Warehouse addInventory(WarehouseInventory item) {
		inventory.add(item);
		return this;
	}
	
	public Warehouse removeInventory(WarehouseInventory item) {
		inventory.remove(item);
		return this;
	}

	public WarehouseInventory findWarehouseInventory(Item item)
	{
		for(WarehouseInventory inventoryItem : inventory) if(inventoryItem.ofItem(item.getName())) return inventoryItem;
		return null;
	}
}
