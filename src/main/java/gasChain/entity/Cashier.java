package gasChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cashier extends Employee {
	
	private String name;
	private float wagesHourly;
	private int hoursWeekly;

    @ManyToOne
    @JoinColumn(name = "workplace_id")
    private GasStation workplace;
    
    // Constructors
    protected Cashier() { super(); }
    public Cashier(String username, String password,
    				String name, float wagesHourly, int hoursWeekly)
    {
    	super(username, password);
    	this.name = name; this.wagesHourly = wagesHourly; this.hoursWeekly = hoursWeekly; 
    }
    public Cashier(String name, float wagesHourly, int hoursWeekly) 
    { 
    	super((name.replace(' ', '_') + (new Integer((int)wagesHourly + hoursWeekly + name.length())).toString()), "password");
    	this.name = name; this.wagesHourly = wagesHourly; this.hoursWeekly = hoursWeekly; 
    }
    public Cashier(String name, float wagesHourly, int hoursWeekly, GasStation workplace)
    {
    	this(name, wagesHourly, hoursWeekly); this.workplace = workplace;
    }
    
    public String getName() { return name; }
    public float getWagesHourly() { return wagesHourly; }
    public int getHoursWeekly() { return hoursWeekly; }
    public GasStation getWorkplace() { return workplace; }
    
    @Override
	public String getAuth() { return "cashier"; }
}
