package gasChain.entity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@MappedSuperclass
public abstract class Inventory implements Serializable {

	@NotNull
	private int price;

	@NotNull
	private int quantity;

	Inventory() {
	}

	public Inventory(int price, int quantity) {
		this.price = price;
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
