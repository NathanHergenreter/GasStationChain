package gasChain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cashier extends Employee {

    private String name;
    private int wagesHourly;
    private int hoursWeekly;

    @ManyToOne
    @JoinColumn(name = "workplace_id")
    private GasStation workplace;

    @Cascade({CascadeType.PERSIST})
    @OneToOne(mappedBy = "cashier")
    private Availability availability;

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
    public void setName(String name) { this.name = name;}

    public int getWagesHourly() {
        return wagesHourly;
    }
    public void setWagesHourly(int hourlyWage) { wagesHourly = hourlyWage; }

    public int getHoursWeekly() {
        return hoursWeekly;
    }
    public void setHoursWeekly(int weeklyHours) { hoursWeekly = weeklyHours; }

    public GasStation getWorkplace() {
        return workplace;
    }
    
    public Availability getAvailability() { return availability; }
    public void setAvailability(Availability availability) { this.availability = availability; }
    
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
