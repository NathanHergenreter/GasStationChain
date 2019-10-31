package gasChain.entity;

import javax.persistence.Entity;

@Entity
public class DebitAccount extends CardAccount {

    private int accountBalance;

    DebitAccount() {
        super();
    }

    public DebitAccount(String cardNumber, int accountBalance) throws Exception {
        super(cardNumber);
        this.accountBalance = accountBalance;
    }
}
