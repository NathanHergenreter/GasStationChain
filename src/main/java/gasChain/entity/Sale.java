package gasChain.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Sale {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String type;
//	private String typeClass ??? - Group items into classes eg Food, drinks, etc
	private float price;
	private Date sellDate;

    @ManyToOne
    @JoinColumn(name = "sellLocation_id")
    private GasStation sellLocation;
	
    // Constructors
    protected Sale() {}
    public Sale(String type, float cost, Date sellDate, GasStation sellLocation)
    {
    	this.type = type; this.price = cost; this.sellDate = sellDate; this.sellLocation = sellLocation;
    	sellLocation.addSale(this);
    }
    
    public String getType() { return type; }
    public float getPrice() { return price; }
    public Date getSellDate() { return sellDate; }
    public GasStation getSellLocation() { return sellLocation; }
    
}
