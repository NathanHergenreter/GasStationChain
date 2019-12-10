package gasChain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Tax tax;

    private String name;
    private int suggestRetailPrice;

    protected Item() {
    }

    public Item(String name, int suggestRetailPrice) {
        this.name = name;
        this.suggestRetailPrice = suggestRetailPrice;
        this.tax = null;
    }

    public Item(String name, float suggestRetailPrice) {
        this(name, (int) (suggestRetailPrice * 100));
    }

    public Long getId() {
        return id;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSuggestRetailPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return getSuggestRetailPrice() == item.getSuggestRetailPrice() &&
                getId().equals(item.getId()) &&
                getName().equals(item.getName());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuggestRetailPrice() {
        return suggestRetailPrice;
    }
}
