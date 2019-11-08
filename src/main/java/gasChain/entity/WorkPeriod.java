package gasChain.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class WorkPeriod {

    int startHour;
    int endHour;
    int wages;
    Date date;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cashier_id")
    private Cashier cashier;

    protected WorkPeriod() {
    }

    public WorkPeriod(Cashier cashier, int startHour, int endHour, int wages, Date date) {
        this.cashier = cashier;
        this.startHour = startHour;
        this.endHour = endHour;
        this.wages = wages;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public Cashier getCashier() {
        return cashier;
    }
}
