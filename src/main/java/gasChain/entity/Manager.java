package gasChain.entity;

import javax.persistence.Entity;

@Entity
public class Manager extends User {

	public Manager(String username, String password) {
		super(username, password);
	}

}
