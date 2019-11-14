package gasChain.entity;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String state;
    private String region;

    protected Store() {
    }

    public Store(String location, String state, String region) {
        this.location = location;
        this.state = state;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
