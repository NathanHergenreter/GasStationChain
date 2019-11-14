package gasChain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Cascade({CascadeType.PERSIST})
    @OneToMany(mappedBy = "receipt")
    private List<Sale> sales = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    private Payment payment;

    public Receipt() {
    }

    public Receipt(List<Sale> sales) {
        this.sales = sales;
    }

    public Receipt(Sale sale) {
        sales.add(sale);
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public Sale getSale(String itemType) {
        for (Sale sale : sales) {
            String saleType = sale.getItem().getName();
            if (saleType.equals(itemType)) {
                return sale;
            }
        }
        return null;
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }

    // Note - do not change order - add new to end
    public enum Payment {
        INVALID, CREDIT, DEBIT, CASH
    }
}
