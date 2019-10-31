package gasChain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Cashier extends Employee {

    private String _name;
    private int _wagesHourly;
    private int _hoursWeekly;

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
        this._name = name;
        this._wagesHourly = wagesHourly;
        this._hoursWeekly = hoursWeekly;
    }

    public Cashier(String name, int wagesHourly, int hoursWeekly) {
        super((name.replace(' ', '_') + (Integer.valueOf(wagesHourly + hoursWeekly + name.length())).toString()), "password");
        this._name = name;
        this._wagesHourly = wagesHourly;
        this._hoursWeekly = hoursWeekly;
    }

    public Cashier(String name, int wagesHourly, int hoursWeekly, GasStation workplace) {
        this(name, wagesHourly, hoursWeekly);
        this.workplace = workplace;
    }

    public String getName() {
        return _name;
    }
    public void setName(String name) { _name = name;}

    public int getWagesHourly() {
        return _wagesHourly;
    }
    public void setWagesHourly(int hourlyWage) { _wagesHourly = hourlyWage; }

    public int getHoursWeekly() {
        return _hoursWeekly;
    }
    public void setHoursWeekly(int weeklyHours) { _hoursWeekly = weeklyHours; }

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
