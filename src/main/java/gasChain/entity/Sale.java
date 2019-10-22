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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    public Sale(String type, float price, Date sellDate)
    {
    	this.type = type; this.price = price; this.sellDate = sellDate;
    }
    public Sale(String type, float price, Date sellDate, GasStation sellLocation)
    {
    	this(type, price, sellDate); this.sellLocation = sellLocation;
    }
    public Sale(Sale sale)
    {
    	this(sale.getType(), sale.getPrice(), sale.getSellDate()); 
    }
    public Sale(Sale sale, GasStation sellLocation)
    {
    	this(sale); this.sellLocation = sellLocation;
    }
    
    public String getType() { return type; }
    public float getPrice() { return price; }
    public Date getSellDate() { return sellDate; }
    public GasStation getSellLocation() { return sellLocation; }
    public Sale addSellLocation(GasStation sellLocation) { this.sellLocation = sellLocation; sellLocation.addSale(this); return this; }
    
}
