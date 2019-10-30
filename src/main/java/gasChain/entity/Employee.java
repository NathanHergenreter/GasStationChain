package gasChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance
public abstract class Employee {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	private String username;
	private String password;
	
	protected Employee() {}
	public Employee(String username, String password) { this.username = username; this.password = password; }
	
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	
	public String getAuth() { return "generic"; }
	
	public boolean isAuth(String authority) { return getAuth().equals(authority); }
}
