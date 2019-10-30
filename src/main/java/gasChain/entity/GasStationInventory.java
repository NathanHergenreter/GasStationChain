package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "gas_station_inventories")
public class GasStationInventory extends Inventory {

    @Id
    @ManyToOne
    @JoinColumn(name="gas_station")
    private GasStation gasStation;

    private int maxQuantity;

    public GasStationInventory(Item item, @NotNull float price, @NotNull int quantity) {
        super(item, price, quantity); this.maxQuantity = quantity;
    }
    
    public GasStationInventory(Item item, @NotNull float price, @NotNull int quantity, int maxQuantity) {
        super(item, price, quantity); this.maxQuantity = maxQuantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
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
