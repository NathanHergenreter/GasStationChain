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
    public void execute(List<String> cmd) throws Exception {
        String command = cmd.get(0);
        
        switch (command) {
	        case "RestockInventory":
	        	corporateHelper.restockWarehouseInventory(cmd.subList(1,cmd.size()));
	        	break;
	        case "AddManager":
	        	corporateHelper.addManager(cmd.subList(1,cmd.size()));
	        	break;
	        case "RemoveManager":
	        	corporateHelper.removeManager(cmd.subList(1,cmd.size()));
	        	break;
	        case "AddGasStation":
	        	corporateHelper.addGasStation(cmd.subList(1,cmd.size()));
	        	break;
	        case "RemoveGasStation":
	        	corporateHelper.removeGasStation(cmd.subList(1,cmd.size()));
	        	break;
	        	
	        default:
	        	System.out.println("Invalid command: " + command);
	            break;
        }
    }
}
