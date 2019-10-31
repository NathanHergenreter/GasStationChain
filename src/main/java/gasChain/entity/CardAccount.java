package gasChain.entity;

import gasChain.util.Luhn;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public abstract class CardAccount extends Payment {

    @Column(columnDefinition = "CHAR(16)")
    private String cardNumber;

    @OneToMany(mappedBy = "cardAccount")
    private Set<Sale> sales = new HashSet<>();

    @LastModifiedDate
    private Date lastUsed;

    @CreatedDate
    private Date createdOn;

    CardAccount() {
    }

    public CardAccount(String cardNumber) throws Exception {
        //May create some exception classes later...
        if (!Luhn.validate(cardNumber)) {
            throw new IllegalArgumentException("Invalid Credit Card Number");
        }
        this.cardNumber = cardNumber;
        this.createdOn = new Date();
    }
}
