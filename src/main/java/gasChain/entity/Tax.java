package gasChain.entity;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "taxes")
public class Tax {

    @Id
    private String type;

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "tax")
    private List<Item> items = new ArrayList<>();

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "tax")
    private List<Employee> employees = new ArrayList<>();

    Float multiplier;

    protected Tax() {
    }

    public Tax(String type, Float multiplier) {
        this.type = type;
        this.multiplier = multiplier;
    }

    public Tax(String type, Float multiplier, Item item) {
        this.type = type;
        this.multiplier = multiplier;
        items.add(item);
    }

    public Tax(String type, Float multiplier, Employee employee) {
        this.type = type;
        this.multiplier = multiplier;
        employees.add(employee);
    }

    public String getId() {
        return type;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) { items.add(item); }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> Employees) {
        this.employees = Employees;
    }

    public void addEmployee(Employee employee) { employees.add(employee); }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Float multiplier) {
        this.multiplier = multiplier;
    }
}
