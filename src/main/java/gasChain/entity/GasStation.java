package gasChain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GasStation extends Store {

    @Cascade({CascadeType.ALL})
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "gasStation")
    private List<GasStationInventory> inventory = new ArrayList<>();

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "sellLocation")
    private List<Sale> sales = new ArrayList<>();

    @Cascade({CascadeType.PERSIST})
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @Cascade({CascadeType.ALL})
    @OneToOne
    @JoinColumn(name = "expenses_id")
    private Expenses expenses;

    @Cascade({CascadeType.SAVE_UPDATE})
    @OneToMany(mappedBy = "workplace")
    private List<Cashier> cashiers = new ArrayList<>();

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "gasStation")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Promotion> promotions = new ArrayList<>();

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "gasStation")
    private List<GasTankInventory> gasTanks = new ArrayList<>();

    protected GasStation() {
        super();
    }

    public GasStation(String location, String state, String region) {
        super(location, state, region);
        this.expenses = new Expenses(0, 0, 0, 0, 0);
    }

    public List<GasStationInventory> getInventory() {
        return inventory;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public GasStation setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
        return this;
    }

    public GasStation addPromotion(Promotion promotion) {
        promotions.add(promotion);
        return this;
    }

    public boolean hasPromotion(Item item) {
        String type = item.getName();
        for (Promotion promotion : promotions) {
            if (promotion.getItem().getName() == item.getName()) {
                return true;
            }
        }
        return false;
    }

    public GasStation setInventory(List<GasStationInventory> inventory) {
        this.inventory = inventory;
        return this;
    }

    public GasStation addInventory(GasStationInventory item) {
        inventory.add(item);
        return this;
    }
    
    public boolean hasInventory(Item item)  {
    	String type = item.getName();
    	for(GasStationInventory inventoryItem : inventory)
    	{
    		if(inventoryItem.ofItem(type)) { return true; }
    	}
    	return false;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public GasStation addSale(Sale sale) {
        sales.add(sale);
        return this;
    }

    public Manager getManager() {
        return manager;
    }

    public GasStation setManager(Manager manager) {
        this.manager = manager;
        return this;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public GasStation addCashier(Cashier cashier) {
        cashiers.add(cashier);
        return this;
    }
    
    public Expenses getExpenses() {
    	return expenses;
    }
    
    public GasStation setExpenses(Expenses expenses) {
    	this.expenses = expenses;
    	return this;
    }
    
    public GasStation updateExpenses(Expenses expenses) {
    	this.expenses.update(expenses);
    	return this;
    }
}
