package gasChain.entity;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class CreditCardAccount extends CardAccount {

    CreditCardAccount() {
        super();
    }

    public CreditCardAccount(String cardNumber, Set<Sale> sales) throws Exception {
        super(cardNumber, sales);
    }
}
