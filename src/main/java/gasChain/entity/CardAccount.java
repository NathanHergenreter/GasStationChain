package gasChain.entity;

import gasChain.util.Luhn;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import java.util.Date;

@Entity
@Inheritance
public abstract class CardAccount extends Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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
