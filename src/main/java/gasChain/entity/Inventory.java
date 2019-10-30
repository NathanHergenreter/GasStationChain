package gasChain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance
public abstract class Inventory implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Item item;

    @NotNull
    private float price;

    @NotNull
    private int quantity;
    
    protected Inventory() {}
    
    public Inventory(Item item, float price, int quantity) {
        this.item = item; this.price = price; this.quantity = quantity;
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

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
