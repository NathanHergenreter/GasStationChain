package gasChain.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private int price;

    @CreatedDate
    private Date sellDate;

    @OneToOne
    @NotNull
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne
    @NotNull
    @JoinColumn(name = "gas_station_id")
    private GasStation sellLocation;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    private boolean isReturned;

    protected Sale() {
    }

    public Sale(@NotNull Item item, @NotNull GasStation sellLocation, @NotNull Receipt receipt, int price) {
        this.receipt = receipt;
        this.price = price;
        this.item = item;
        this.sellLocation = sellLocation;
        this.isReturned = false;
        this.sellDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
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

    public boolean getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return getPrice() == sale.getPrice() &&
                isReturned == sale.isReturned &&
                getId().equals(sale.getId()) &&
                getSellDate().equals(sale.getSellDate()) &&
                getItem().equals(sale.getItem()) &&
                getSellLocation().equals(sale.getSellLocation()) &&
                receipt.equals(sale.receipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getSellDate(), getItem(), getSellLocation(), receipt, isReturned);
    }

}
