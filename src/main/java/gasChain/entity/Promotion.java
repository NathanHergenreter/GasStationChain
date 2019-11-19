package gasChain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "promotions")
@IdClass(PromotionCompositeId.class)
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    @JoinColumn
    private Item item;
    
    @ManyToOne
    @JoinColumn(name = "gas_station_id")
    private GasStation gasStation;

    @Id
    @ManyToOne
    @JoinColumn
    private GasStation gasStation;

    private Date startDate;

    private Date endDate;

    private float priceMultiplier;

    protected Promotion() {
    }

    public Promotion(Item item, float priceMultiplier, Date startDate, Date endDate) {
        this.item = item;
        this.priceMultiplier = priceMultiplier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item Item) {
        this.item = Item;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    public float getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(float priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date startDate) { this.endDate = endDate; }

    @Override
    public int hashCode() {
        return Objects.hash(getGasStation(), getItem());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotion that = (Promotion) o;
        return getGasStation().equals(that.getGasStation()) && getItem().equals(that.getItem());
    }
}

class PromotionCompositeId implements Serializable {
    private Long item;
    private Long gasStation;

    public PromotionCompositeId() {
    }

    public PromotionCompositeId(Long item, Long gasStation) {
        this.item = item;
        this.gasStation = gasStation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, gasStation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PromotionCompositeId that = (PromotionCompositeId) o;
        return item.equals(that.item) && Objects.equals(gasStation, that.gasStation);
    }
}