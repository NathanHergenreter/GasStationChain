package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "warehouse_inventories")
public class WarehouseInventory  implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Warehouse warehouse;

    @Id
    @ManyToOne
    @JoinColumn
    private Item item;

    @NotNull
    private float price;

    @NotNull
    private int quantity;

    public WarehouseInventory(Item item, float price, int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }
    public Item getItem() {
        return item;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }


    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
