package gasChain.application.manager;

import gasChain.application.manager.interfaces.ICashierHelper;
import gasChain.util.ServiceAutoWire;
import gasChain.entity.*;
import gasChain.entity.Receipt.Payment;
import gasChain.service.*;

import java.text.DecimalFormat;
import java.util.*;


public class CashierHelper implements ICashierHelper {

    private static CashierHelper cashierHelperSingleton;
    private Cashier cashier;
    private WorkPeriodService workPeriodService = ServiceAutoWire.getBean(WorkPeriodService.class);
    private ReceiptService receiptService = ServiceAutoWire.getBean(ReceiptService.class);
    private GasStationInventoryService gasStationInventoryService = ServiceAutoWire.getBean(GasStationInventoryService.class);
    private ItemService itemService = ServiceAutoWire.getBean(ItemService.class);
    private SaleService saleService = ServiceAutoWire.getBean(SaleService.class);
    private CashierService cashierService = ServiceAutoWire.getBean(CashierService.class);


    private CashierHelper(Cashier cashier) {
        this.cashier = cashier;
    }

    public static CashierHelper cashierHelper(Cashier cashier) {
        if (cashierHelperSingleton == null) {
            return new CashierHelper(cashier);
        } else {
            return cashierHelperSingleton;
        }
    }


    /*
    expected use case is every employee doesn't add a WorkPeriod until after their shift
    NOTE: given hours should be on a 24 hour basis, also assumed shifts will not be greater than 24 hour period
    args: -startHour -endHour
     */
    @Override
    public void addWorkPeriod(List<String> args) {
        WorkPeriod workPeriod = new WorkPeriod(
                cashier,
                Integer.parseInt(args.get(0)),
                Integer.parseInt(args.get(1)),
                cashier.getWagesHourly(),
                new java.sql.Date(System.currentTimeMillis())
        );
        
        this.workPeriodService.save(workPeriod);
        this.cashierService.save(cashier);
    }

    private String getInput(Scanner in) throws Exception {
        String input = in.nextLine();
        if (input.equals("/end")) {
            throw new Exception("/endCalled");
        } else return input;
    }

    @Override
    public void processSale() throws Exception {
        Scanner in = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        GasStation gasStation = cashier.getWorkplace();
        Receipt receipt = receiptService.save(new Receipt());

        ArrayList<GasStationInventory> inventoryItems = processSaleGetInventory(in, gasStation, receipt, df);
        
        Payment paymentType = processSaleGetPayment(in, df);
        receipt.setPayment(paymentType);

        for (GasStationInventory inventoryItem : inventoryItems) 
        {
            gasStationInventoryService.RemoveItemFromInventory(inventoryItem.getGasStation(), inventoryItem.getItem());
            gasStationInventoryService.save(inventoryItem);
        }
        
        System.out.println("End of Sale");
        receiptService.save(receipt);
    }
    
    private ArrayList<GasStationInventory> processSaleGetInventory(Scanner in, 
    										GasStation gasStation, Receipt receipt, DecimalFormat df)
    {
    	ArrayList<GasStationInventory> inventoryItems = new ArrayList<GasStationInventory>();
    	
        System.out.println("New Order Started With ID: " + receipt.getId());
        System.out.println("Please proceed to enter item type");
        System.out.println("To end transaction enter /end");

        int total = 0;
        for(String itemType = in.nextLine(); !itemType.equals("/end"); itemType = in.nextLine())
        {
            Item item = itemService.findByName(itemType);
            GasStationInventory inventoryItem = gasStationInventoryService.findGasStationInventoryByGasStationAndItem(gasStation, item);
            inventoryItems.add(inventoryItem);
            
            int price = inventoryItem.getPrice();
            total += price;
            
            Sale s = new Sale(item, gasStation, receipt, price);
            receipt.addSale(s);
            
            System.out.println(item.getId() + " --- " + item.getName() + " ---  $" + df.format(price / 100.0) + " --- Total = " + df.format(total / 100.0));
        }
        
        return inventoryItems;
    }
    
    private Payment processSaleGetPayment(Scanner in, DecimalFormat df) throws Exception
    {
    	Payment paymentType = Receipt.Payment.INVALID;
    	
    	boolean invalid = true;
    	while(invalid)
    	{
	        System.out.println("How will the customer be paying today? (enter /end to end processing)");
	        System.out.println("1 - Credit Card");
	        System.out.println("2 - Debit Card");
	        System.out.println("3 - Cash");
	        
	        String input = getInput(in);
	        
	        if(!input.equals("/end"))
	        {
		        paymentType = Receipt.Payment.values()[new Integer(input)];
		        
		        invalid = processPayment(in, paymentType, df);
		        
		        if(invalid) 
		        { 
		        	System.out.println("Invalid payment method"); 
		        	paymentType = Receipt.Payment.INVALID;
		        }
	        }
    	}
    	
    	return paymentType;
    }

    private boolean processPayment(Scanner in, Receipt.Payment paymentType, DecimalFormat df) throws Exception 
    {
        int modDenominator = 1000;	// Effectively 1 in modDenominator card numbers will be invalid
        
        boolean continueProcessing = true;
        while(continueProcessing)
        {
	        switch(paymentType)
	        {
		        case CREDIT: 
		        {
		            System.out.println("Enter Credit Card Number: ");
		            int cardNumber = new Integer(getInput(in));
		            
		            if(cardNumber % modDenominator == 0) 
		            { 
		            	continueProcessing = invalidCard(in, cardNumber);
		            	if(!continueProcessing) return false;
		            }
		            else { return true; }
		            
		            break;
		        } 
		        
		        case DEBIT:
		        {
		            System.out.println("Enter Credit Card Number: ");
		            int cardNumber = new Integer(getInput(in));
		            
		            if(cardNumber % modDenominator == 0) 
		            { 
		            	continueProcessing = invalidCard(in, cardNumber);
		            	if(!continueProcessing) return false;
		            }
		            else { return true; }
		            
		            break;
		        } 
		        
		        case CASH:
		        {
		            return true;
		        }
		        
		        default:
		        	return false;
	        }
        }
		return false;
    }

    // Returns true if /end was not entered (i.e. cancel the processing, otherwise retry)
    private boolean invalidCard(Scanner in, int cardNumber) throws Exception 
    {
        System.out.println("Invalid Card Number " + cardNumber);
        System.out.println("Enter /end to exit, otherwise to retry");
        return !in.nextLine().equals("/end");
    }

    @Override
    public void processReturn(List<String> args) throws Exception {
        if (args == null || args.size() <= 2)
            throw new Exception("Invalid minimum number of args for 'ReturnItems' (2)");
        
        Long id = new Long(args.get(0));
        Receipt saleReceipt = receiptService.findById(id);

        if(saleReceipt == null)
            throw new Exception("Receipt of id '" + id + "' does not exist.");
        
        Receipt returnReceipt = receiptService.save(new Receipt());
        for(int i = 1; i < args.size(); i++)
        {
        	String itemType = args.get(i);
        	Sale sale = saleReceipt.getSale(itemType);
        	
        	if(sale == null)
                throw new Exception("Item '" + itemType + "' is not on receipt.");
        	
        	returnReceipt.addSale( returnItem(
        								new Sale(sale, returnReceipt,-(sale.getPrice()))
        						));
        }

        System.out.print("Items Returned");
        receiptService.save(returnReceipt);
    }

    private Sale returnItem(Sale sale) {
        GasStationInventory inventoryItem =
                gasStationInventoryService.findGasStationInventoryByGasStationAndItem(cashier.getWorkplace(), sale.getItem());
        inventoryItem.setQuantity(inventoryItem.getQuantity() + 1);
        gasStationInventoryService.save(inventoryItem);
        saleService.save(sale);
        
        return sale;
    }
}


