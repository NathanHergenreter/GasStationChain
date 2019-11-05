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

//	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
//	private Set<WarehouseInventory> inWarehouses = new HashSet<>();
//
//	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
//	private Set<GasStationInventory> inGasStations = new HashSet<>();

	private String description;
	private String name;
	private int suggestRetailPrice;

	protected Item() {
	}

	public Item(String name, int suggestRetailPrice) {
		this.name = name;
		this.suggestRetailPrice = suggestRetailPrice;
	}

	public Item(String name, float suggestRetailPrice) {
		this(name, (int) (suggestRetailPrice * 100));
	}

	public Item(String name, String description, int suggestRetailPrice) {
		this.name = name;
		this.description = description;
		this.suggestRetailPrice = suggestRetailPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Set<WarehouseInventory> getInWarehouses() {
//		return inWarehouses;
//	}
//
//	public Set<GasStationInventory> getInGasStations() {
//		return inGasStations;
//	}

	public int getSuggestRetailPrice() {
		return suggestRetailPrice;
	}

}
