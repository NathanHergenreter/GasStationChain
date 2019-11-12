package gasChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne(mappedBy = "expenses")
    private GasStation store;
    
    private int electric;
    private int water;
    private int sewage;
    private int garbage;
    private int insurance;
    
    protected Expenses() {}
    
    public Expenses(int electric, int water, int sewage, int garbage, int insurance)
    {
    	this.electric = electric; this.water = water; this.sewage = sewage; 
    	this.garbage = garbage; this.insurance = insurance;
    }
    
    public Expenses update(Expenses expenses)
    {
    	this.electric = expenses.electric; this.water = expenses.water; 
    	this.sewage = expenses.sewage; this.garbage = expenses.garbage; 
    	this.insurance = expenses.insurance;
    	
    	return this;
    }
    
    public int getElectric() { return electric; }
    public Expenses setElectric(int electric) { this.electric = electric; return this; }
    public int getWater() { return water; }
    public Expenses setWater(int water) { this.water = water; return this; }
    public int getSewage() { return sewage; }
    public Expenses setSewage(int sewage) { this.sewage = sewage; return this; }
    public int getGarbage() { return garbage; }
    public Expenses setGarbage(int garbage) { this.garbage = garbage; return this; }
    public int getInsurance() { return insurance; }
    public Expenses setInsurance(int insurance) { this.insurance = insurance; return this; }
}
