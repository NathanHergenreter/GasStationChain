package gasChain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class GasStation {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	private String location;
	private String state;
	private String region;

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "sellLocation")
	private List<Sale> sales = new ArrayList<Sale>();

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "workplace")
	private List<Employee> employees = new ArrayList<Employee>();
	
	//Default Constructor
	protected GasStation() {}
	public GasStation(String location) { this.location = location; }
	public GasStation(String location, String state, String region) { this.location = location; this.state = state; this.region = region; }
	
	
	// Stupid-ass getters and setters fuck Java
	// If you visually hate them or just hate the boilerplate in general look into projectlombok 
	public String getLocation() { return location; }
	public String getState() { return state; }
	public String getRegion() { return region; }
	public List<Sale> getSales() { return sales; }
	public void addSale(Sale sale) { sales.add(sale); }
	public List<Employee> getEmployees() { return employees; }
	public void addEmployee(Employee employee) { employees.add(employee); }
}
