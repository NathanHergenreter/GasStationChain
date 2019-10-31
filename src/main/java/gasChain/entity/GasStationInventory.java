package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "gas_station_inventory_items")
@IdClass(GasStationInventoryCompositeId.class)
public class GasStationInventory extends Inventory {

	@Id
	@ManyToOne
	@JoinColumn
	private Item item;

	@Id
	@ManyToOne
	@JoinColumn
	private GasStation gasStation;

	private int maxQuantity;

	protected GasStationInventory() {
		super();
	}

	public GasStationInventory(Item item, @NotNull int price, @NotNull int quantity) {
		super(price, quantity);
		this.maxQuantity = quantity;
		this.item = item;
		this.gasStation = null;
	}

	public GasStationInventory(Item item, @NotNull int price, @NotNull int quantity, int maxQuantity) {
		super(price, quantity);
		this.maxQuantity = maxQuantity;
		this.item = item;
		this.gasStation = null;
	}

	public int getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public Item getItem() {
		return item;
	}

	public GasStation getGasStation() {
		return gasStation;
	}

	public void setGasStation(GasStation gasStation) {
		this.gasStation = gasStation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GasStationInventory that = (GasStationInventory) o;
		return getGasStation().equals(that.getGasStation()) && getItem().equals(that.getItem());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getGasStation(), getItem());
	}

}

class GasStationInventoryCompositeId implements Serializable {
	private Item item;
	private GasStation gasStation;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GasStationInventoryCompositeId that = (GasStationInventoryCompositeId) o;
		return item.equals(that.item) && Objects.equals(gasStation, that.gasStation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(item, gasStation);
	}
}