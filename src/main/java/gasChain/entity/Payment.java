package gasChain.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance
@DiscriminatorColumn(name = "payment_type")
public abstract class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Cascade({org.hibernate.annotations.CascadeType.PERSIST})
    @OneToMany(mappedBy = "payment")
    private List<Receipt> transactions = new ArrayList<>();

    public Payment() {
    }

    public List<Receipt> getTransactions() {
        return transactions;
    }

    public List<Receipt> getTransactionsByItem(long item_id) {
        List<Receipt> receipts = new ArrayList<>();
        for (int i = 0; i < transactions.size(); i++) {
            List<Sale> sales = transactions.get(i).getSales();
            for (int j = 0; j < sales.size(); j++) {
                if (sales.get(j).getItem().getId() == item_id) {
                    receipts.add(transactions.get(i));
                }
            }
        }
        return receipts;
    }


}
