package gasChain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Cashier extends Employee {

    private String name;
    private int wagesHourly;
    private int hoursWeekly;

    @ManyToOne
    @JoinColumn(name = "workplace_id")
    private GasStation workplace;

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "cashier")
    private List<Availability> availabilities = new ArrayList<>();

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "cashier")
    private List<WorkPeriod> workPeriods = new ArrayList<>();

    protected Cashier() {
        super();
    }

    public Cashier(String username, String password,
                   String name, int wagesHourly, int hoursWeekly) {
        super(username, password);
        this.name = name;
        this.wagesHourly = wagesHourly;
        this.hoursWeekly = hoursWeekly;
    }

    public Cashier(String name, int wagesHourly, int hoursWeekly) {
        super((name.replace(' ', '_') + (Integer.valueOf(wagesHourly + hoursWeekly + name.length())).toString()), "password");
        this.name = name;
        this.wagesHourly = wagesHourly;
        this.hoursWeekly = hoursWeekly;
    }

    public Cashier(String name, int wagesHourly, int hoursWeekly, GasStation workplace) {
        this(name, wagesHourly, hoursWeekly);
        this.workplace = workplace;
    }

    public String getName() {
        return name;
    }

    public int getWagesHourly() {
        return wagesHourly;
    }

    public int getHoursWeekly() {
        return hoursWeekly;
    }

    public GasStation getWorkplace() {
        return workplace;
    }
    
    public List<Availability> getAvailabilities() { return availabilities; }
    public Cashier addAvailability(Availability availability) { availabilities.add(availability); return this; }
    public Cashier addAvailabilities(ArrayList<Availability> availabilities)
    { 
    	this.availabilities.addAll(availabilities); 
    	return this; 
    }
    
    public List<WorkPeriod> getWorkPeriods() { return workPeriods; }
    public Cashier addWorkPeriod(WorkPeriod workPeriod) { workPeriods.add(workPeriod); return this; }
    public Cashier addWorkPeriods(ArrayList<WorkPeriod> workPeriods)
    {
    	this.workPeriods.addAll(workPeriods); 
    	return this; 
    }

    @Override
    public String getAuth() {
        return "cashier";
    }
}
