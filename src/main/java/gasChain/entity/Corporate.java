package gasChain.entity;

import javax.persistence.Entity;

@Entity
public class Corporate extends Employee {

	protected Corporate() { super(); }
	public Corporate(String username, String password) {
		super(username, password);
	}
	
	@Override
	public boolean auth() { return true; }

}
