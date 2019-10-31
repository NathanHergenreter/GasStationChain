package gasChain.entity;

import gasChain.util.Luhn;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance
public class CardAccount {

    private String cardNumber;

    @OneToMany(mappedBy = "cardAccount")
    private Set<Sale> sales;

    @LastModifiedDate
    private Date lastUsed;

    @CreatedDate
    private Date createdOn;

    CardAccount() {
    }

    public CardAccount(String cardNumber, Set<Sale> sales) throws Exception {
        //May create some exception classes later...
        if (!Luhn.validate(cardNumber)) {
            throw new IllegalArgumentException("Invalid Credit Card Number");
        }
        this.cardNumber = cardNumber;
        this.sales = sales;
        this.createdOn = new Date();
    }
}
