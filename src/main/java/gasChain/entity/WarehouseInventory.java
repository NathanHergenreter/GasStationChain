package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "warehouse_inventories")
public class WarehouseInventory  extends Inventory {

    @Id
    @ManyToOne
    @JoinColumn
    private Warehouse warehouse;

    public WarehouseInventory(Item item, float price, int quantity) {
        super(item, price, quantity);
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseInventory that = (WarehouseInventory) o;
        return getWarehouse().equals(that.getWarehouse()) &&
                getItem().equals(that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWarehouse(), getItem());
    }
}
