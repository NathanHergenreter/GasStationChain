package gasChain.entity;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receipts")
public class Receipt {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Cascade({ CascadeType.PERSIST })
	@OneToMany(mappedBy = "receipt")
	private List<Sale> sales = new ArrayList<>();

	public Receipt() {
	}

	public Receipt(List<Sale> sales) {
		this.sales = sales;
	}

	public Receipt(Sale sale) {
		sales.add(sale);
	}

	public Long getId() {
		return id;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void addSale(Sale sale) {
		sales.add(sale);
	}

	public void voidSale(Sale sale) {
		sales.remove(sale);
	}

}
