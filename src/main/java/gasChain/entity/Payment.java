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


}
