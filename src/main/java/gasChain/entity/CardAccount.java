package gasChain.entity;

import gasChain.util.Luhn;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public abstract class CardAccount extends Payment {

    @Column(columnDefinition = "CHAR(16)")
    private String cardNumber;

    @LastModifiedDate
    private Date lastUsed;

    CardAccount() {
    }

    public CardAccount(String cardNumber) throws Exception {
        //May create some exception classes later...
        if (!Luhn.validate(cardNumber)) {
            throw new IllegalArgumentException("Invalid Credit Card Number");
        }
        this.cardNumber = cardNumber;

    }
}
