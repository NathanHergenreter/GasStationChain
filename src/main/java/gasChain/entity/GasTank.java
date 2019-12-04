package gasChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class GasTank {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne(mappedBy = "gasTank")
    private GasStation gasStation;
    
    private int regularAmount;
    private int midGradeAmount;
    private int premiumAmount;
    private int bioDieselAmount;
    private int ethanolAmount;
    private int regularMax;
    private int midGradeMax;
    private int premiumMax;
    private int bioDieselMax;
    private int ethanolMax;
    
    protected GasTank() {}
    
    public GasTank(int regular, int midGrade, int premium, int bioDiesel, int ethanol)
    {
    	this.regularAmount = regular;
    	this.midGradeAmount = midGrade;
    	this.premiumAmount = premium;
    	this.bioDieselAmount = bioDiesel;
    	this.ethanolAmount = ethanol;

    	this.regularMax = regular;
    	this.midGradeMax = midGrade;
    	this.premiumMax = premium;
    	this.bioDieselMax = bioDiesel;
    	this.ethanolMax = ethanol;
    }
    
    public void pumpGas(String type, int amount)
    {
    	if(!validPump(type, amount)) return;
    	
    	switch(type)
    	{
	    	case "regular":
	    		regularAmount -= amount;
	    		break;
	    	case "midGrade":
	    		midGradeAmount -= amount;
	    		break;
	    	case "premium":
	    		premiumAmount -= amount;
	    		break;
	    	case "bioDiesel":
	    		bioDieselAmount -= amount;
	    		break;
	    	case "ethanol":
	    		ethanolAmount -= amount;
	    		break;
    		default:
    			break;
    	}
    }

    public void fillGas(String type, int amount)
    {
    	if(!validFill(type, amount)) return;
    	
    	switch(type)
    	{
	    	case "regular":
	    		regularAmount += amount;
	    		break;
	    	case "midGrade":
	    		midGradeAmount += amount;
	    		break;
	    	case "premium":
	    		premiumAmount += amount;
	    		break;
	    	case "bioDiesel":
	    		bioDieselAmount += amount;
	    		break;
	    	case "ethanol":
	    		ethanolAmount += amount;
	    		break;
    		default:
    			break;
    	}
    }
    
    private boolean validPump(String type, int amount)
    {
    	switch(type)
    	{
	    	case "regular":
	    		return amount <= regularAmount;
	    	case "midGrade":
	    		return amount <= midGradeAmount;
	    	case "premium":
	    		return amount <= premiumAmount;
	    	case "bioDiesel":
	    		return amount <= bioDieselAmount;
	    	case "ethanol":
	    		return amount <= ethanolAmount;
    		default:
    			return false;
    	}
    }
    
    private boolean validFill(String type, int amount)
    {
    	switch(type)
    	{
	    	case "regular":
	    		return regularMax >= regularAmount + amount;
	    	case "midGrade":
	    		return midGradeMax >= midGradeAmount + amount;
	    	case "premium":
	    		return premiumMax >= premiumAmount + amount;
	    	case "bioDiesel":
	    		return bioDieselMax >= bioDieselAmount + amount;
	    	case "ethanol":
	    		return ethanolMax >= ethanolAmount + amount;
    		default:
    			return false;
    	}
    }
}
