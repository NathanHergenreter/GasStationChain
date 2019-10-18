package gasChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Employee {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String name;
	private float wagesHourly;
	private int hoursWeekly;
//	private something type ???

    @ManyToOne
    @JoinColumn(name = "workplace_id")
    private GasStation workplace;
    
    // Constructors
    protected Employee() {}
    public Employee(String name, float wagesHourly, int hoursWeekly, GasStation workplace)
    {
    	this.name = name; this.wagesHourly = wagesHourly; this.hoursWeekly = hoursWeekly; this.workplace = workplace;
    	workplace.addEmployee(this);
    }
    
    public String getName() { return name; }
    public float getWagesHourly() { return wagesHourly; }
    public int getHoursWeekly() { return hoursWeekly; }
    public GasStation getWorkplace() { return workplace; }
}
