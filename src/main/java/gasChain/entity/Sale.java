package gasChain.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
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

    @ManyToOne
    @NotNull
    @JoinColumn(name = "gas_station_id")
    private GasStation sellLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    protected Sale() {}

    public Sale(@NotNull Item item, @NotNull GasStation sellLocation, Receipt receipt, int price) {
        this.receipt = receipt;
        this.price = price;
        this.item = item;
        this.sellLocation = sellLocation;
        this.sellDate = new Date();
    }

    public Sale(@NotNull Item item, @NotNull GasStation sellLocation, @NotNull Receipt receipt, int price, Date sellDate) {
        this(item, sellLocation, receipt, price);
        this.sellDate = sellDate;
    }
    
    // Copy constructor for returns
    public Sale(Sale copySale, Receipt receipt, int price)
    {
    	this(copySale.item, copySale.sellLocation, receipt, price);
    }

    public Long getId() {
        return id;
    }

    public Receipt getReceipt() {
        return receipt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return getPrice() == sale.getPrice() &&
                getId().equals(sale.getId()) &&
                getSellDate().equals(sale.getSellDate()) &&
                getItem().equals(sale.getItem()) &&
                getSellLocation().equals(sale.getSellLocation()) &&
                receipt.equals(sale.receipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getSellDate(), getItem(), getSellLocation(), receipt);
    }

}
