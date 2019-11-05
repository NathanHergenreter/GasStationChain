package gasChain.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WorkPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cashier_id")
    private Cashier cashier;
    
    int startHour;
    int endHour;
    int wages;
    Date date;
    
    protected WorkPeriod() {}
    public WorkPeriod(Cashier cashier, int startHour, int endHour, int wages, Date date)
    {
    	this.cashier = cashier; this.startHour = startHour; this.endHour = endHour; 
    	this.wages = wages; this.date = date;
    }

    public Date getDate() { return date; }
    public int getStartHour() { return startHour; }
    public int getEndHour() { return endHour; }
    public Cashier getCashier() { return cashier;}
}
