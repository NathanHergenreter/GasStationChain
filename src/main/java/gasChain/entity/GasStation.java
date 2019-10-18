package gasChain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class GasStation {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String location;
//	private String region; ???

    @OneToMany(mappedBy = "sellLocation")
	private List<Sale> sales = new ArrayList<Sale>();

    @OneToMany(mappedBy = "workplace")
	private List<Employee> employees = new ArrayList<Employee>();
	
	//Default Constructor
	protected GasStation() {}
	public GasStation(String location) { this.location = location; }
	
	
	// Stupid-ass getters and setters fuck Java
	public String getLocation() { return location; }
	public List<Sale> getSales() { return sales; }
	public void addSale(Sale sale) { sales.add(sale); }
	public List<Employee> getEmployees() { return employees; }
	public void addEmployee(Employee employee) { employees.add(employee); }
}
