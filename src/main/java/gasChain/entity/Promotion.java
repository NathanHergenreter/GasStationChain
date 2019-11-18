package gasChain.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @ManyToOne
    @JoinColumn
    private Item item;

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
}
