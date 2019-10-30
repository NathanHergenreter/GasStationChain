package gasChain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private float price;

    private Date sellDate;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "gas_station_id")
    private GasStation sellLocation;

    private boolean isReturned;

    public Sale(@NotNull Item item, @NotNull GasStation sellLocation, @NotNull float price) {
        this.price = price;
        this.item = item;
        this.sellLocation = sellLocation;
        this.isReturned = false;
    }

    public Long getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public Item getItem() {
        return item;
    }

    public GasStation getSellLocation() {
        return sellLocation;
    }

    public boolean getIsReturned(){
        return isReturned;
    }

    public void setIsReturned(boolean isreturned){
        this.isReturned = isreturned;
    }



}
