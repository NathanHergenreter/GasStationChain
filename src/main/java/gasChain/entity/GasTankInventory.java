package gasChain.entity;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Entity
//@Table(name = "gas_tank_inventory")
//@IdClass(GasTankInventoryCompositeId.class)
public class GasTankInventory extends Inventory {
//
//    @Id
//    @ManyToOne
//    @JoinColumn
//    private Item item;
//
//    @Id
//    @ManyToOne
//    @JoinColumn
//    private GasStation gasStation;
//
//    private int maxQuantity;
//
//    protected GasTankInventory() {
//        super();
//    }
//
//    public GasTankInventory(Item item, @NotNull int price, @NotNull int quantity) {
//        super(price, quantity);
//        this.maxQuantity = quantity;
//        this.item = item;
//        this.gasStation = null;
//    }
//
//    public GasTankInventory(Item item, @NotNull int price, @NotNull int quantity, int maxQuantity) {
//        super(price, quantity);
//        this.maxQuantity = maxQuantity;
//        this.item = item;
//        this.gasStation = null;
//    }
//
//    public int getMaxQuantity() {
//        return maxQuantity;
//    }
//
//    public void setMaxQuantity(int maxQuantity) {
//        this.maxQuantity = maxQuantity;
//    }
//
//    public Item getItem() {
//        return item;
//    }
//
//    public GasStation getGasStation() {
//        return gasStation;
//    }
//
//    public void setGasStation(GasStation gasStation) {
//        this.gasStation = gasStation;
//    }
//
//    public boolean ofItem(String type) {
//        return item.getName().equals(type);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getGasStation(), getItem());
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        GasTankInventory that = (GasTankInventory) o;
//        return getGasStation().equals(that.getGasStation()) && getItem().equals(that.getItem());
//    }
}
//
//class GasTankInventoryCompositeId implements Serializable {
//    private Long item;
//    private Long gasStation;
//
//    public GasTankInventoryCompositeId() {
//    }
//
//    public GasTankInventoryCompositeId(Long item, Long gasStation) {
//        this.item = item;
//        this.gasStation = gasStation;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(item, gasStation);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        GasTankInventoryCompositeId that = (GasTankInventoryCompositeId) o;
//        return item.equals(that.item) && Objects.equals(gasStation, that.gasStation);
//    }
//}
