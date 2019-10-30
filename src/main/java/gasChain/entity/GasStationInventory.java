package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "gas_station_inventories")
public class GasStationInventory implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="gas_station")
    private GasStation gasStation;

    @Id
    @ManyToOne
    @JoinColumn
    private Item item;

    @NotNull
    private float price;

    @NotNull
    private int quantity;

    private int maxQuantity;

    public GasStationInventory(Item item, @NotNull float price, @NotNull int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GasStationInventory that = (GasStationInventory) o;
        return getGasStation().equals(that.getGasStation()) &&
                getItem().equals(that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGasStation(), getItem());
    }

}
