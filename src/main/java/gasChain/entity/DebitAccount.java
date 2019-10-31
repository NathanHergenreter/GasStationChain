package gasChain.entity;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class DebitAccount extends CardAccount {

    private int accountBalance;

    DebitAccount() {
        super();
    }

    public DebitAccount(String cardNumber, Set<Sale> sales, int accountBalance) throws Exception {
        super(cardNumber, sales);
        this.accountBalance = accountBalance;
    }
}
