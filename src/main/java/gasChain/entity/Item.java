package gasChain.entity;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private int suggestRetailPrice;

    protected Item() {
    }

    public Item(String name, int suggestRetailPrice) {
        this.name = name;
        this.suggestRetailPrice = suggestRetailPrice;
    }

    public Item(String name, float suggestRetailPrice) {
        this(name, (int) (suggestRetailPrice * 100));
    }

    public Long getId() {
        return id;
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
