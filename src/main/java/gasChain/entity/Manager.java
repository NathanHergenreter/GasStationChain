package gasChain.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Manager extends Employee {

	@OneToOne(mappedBy = "manager")
	private GasStation store;
	
	protected Manager() { super(); }
	public Manager(String username, String password) {
		super(username, password);
	}
	
	public GasStation getStore() { return store; }
	public Manager setStore(GasStation store) { this.store = store; return this; }

    @Override
	public String getAuth() { return "manager"; }

}
