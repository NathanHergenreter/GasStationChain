package gasChain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "warehouse_inventory_items")
@IdClass(WarehouseInventoryCompositeId.class)
public class WarehouseInventory extends Inventory {

	@Id
	@ManyToOne
	@JoinColumn
	private Item item;

	@Id
	@ManyToOne
	@JoinColumn
	private Warehouse warehouse;

	protected WarehouseInventory() {
		super();
	}

	public WarehouseInventory(Item item, int price, int quantity) {
		super(price, quantity);
		this.item = item;
		this.warehouse = null;
	}

	public Item getItem() {
		return item;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	public boolean ofItem(String type) { return item.getName().equals(type); }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		WarehouseInventory that = (WarehouseInventory) o;
		return getWarehouse().equals(that.getWarehouse()) && getItem().equals(that.getItem());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getWarehouse(), getItem());
	}
}

class WarehouseInventoryCompositeId implements Serializable {
	private Long item;
	private Long warehouse;

	public WarehouseInventoryCompositeId() {}
	
	public WarehouseInventoryCompositeId(Long item, Long warehouse)
	{
		this.item = item; this.warehouse = warehouse;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		WarehouseInventoryCompositeId that = (WarehouseInventoryCompositeId) o;
		return item.equals(that.item) && Objects.equals(warehouse, that.warehouse);
	}

	@Override
	public int hashCode() {
		return Objects.hash(item, warehouse);
	}
}
