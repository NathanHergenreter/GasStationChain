package gasChain.entity;

import javax.persistence.Entity;

@Entity
public class CreditCardAccount extends CardAccount {

    CreditCardAccount() {
        super();
    }

    public CreditCardAccount(String cardNumber) throws Exception {
        super(cardNumber);
    }
}
