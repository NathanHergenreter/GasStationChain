package gasChain.userControllers;

import gasChain.coreInterfaces.managers.ICorporateHelper;
import gasChain.coreInterfaces.userControllers.IUserController;
import gasChain.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CorporateController implements IUserController {
	
    public CorporateController(ICorporateHelper helper){
        corporateHelper = helper;
    }

    private ICorporateHelper corporateHelper;

    @Override
    public void execute(List<String> cmd) {
        String command = cmd.get(0);
        
        //TODO: only pass in cmd e for i>=1
        switch (command) {
        
        case "RestockInventory":
        	System.out.println("Enter name of warehouse followed by a list of item names and a list of quantities");
        	Scanner scan = new Scanner(System.in);
        	String name = scan.next();
        	List<String> items = new ArrayList<String>();
        	List<Integer> quantities = new ArrayList<Integer>();
        	while (!scan.hasNextInt()) {
        		items.add(scan.next());
        	}
        	while (scan.hasNextInt()) {
        		quantities.add(scan.nextInt());
        	}
        	corporateHelper.restockWarehouseInventory(name, items, quantities);
        	
        default:
            break;
        }
    }
}
