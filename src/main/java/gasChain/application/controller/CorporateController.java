package gasChain.application.controller;

import gasChain.application.manager.interfaces.ICorporateHelper;
import gasChain.application.controller.interfaces.IUserController;

import java.util.List;

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
	        case "AddWarehouse":
	        	corporateHelper.addWarehouse(cmd.subList(1,cmd.size()));
	        	break;
	        case "RemoveWarehouse":
	        	corporateHelper.removeWarehouse(cmd.subList(1,cmd.size()));
	        	break;
	        case "AddWarehouseInventory":
	        	corporateHelper.addWarehouseInventory(cmd.subList(1,cmd.size()));
	        	break;
	        case "RemoveWarehouseInventory":
	        	corporateHelper.removeWarehouseInventory(cmd.subList(1,cmd.size()));
	        	break;
	        	
	        default:
	        	System.out.println("Invalid command: " + command);
	            break;
        }
    }
}
