package gasChain.userControllers;

import gasChain.coreInterfaces.managers.IManagerHelper;
import gasChain.coreInterfaces.userControllers.IUserController;
import java.util.List;

public class ManagerController implements IUserController {
    public ManagerController(IManagerHelper helper){
        _managerHelper = helper;
    }

    private IManagerHelper _managerHelper;

    @Override
    public void execute(List<String> cmd) throws Exception {
        String command = cmd.get(0);
        switch (command){
            case "AddCashier":
                _managerHelper.addCashier(cmd.subList(1,cmd.size()));
                break;
            case "UpdateCashier":
                _managerHelper.updateCashier(cmd.subList(1,cmd.size()));
                break;
            case "UpdateCashierAvailability":
                _managerHelper.updateCashierAvailability(cmd.subList(1,cmd.size()));
                break;
            case "RemoveCashier":
                _managerHelper.removeCashier(cmd.subList(1,cmd.size()));
                break;
            case "ListStoreCashiers":
            	_managerHelper.listStoreCashiers();
            	break;
            case "GetCashierPayroll":
                _managerHelper.getCashierPayroll(cmd.subList(1,cmd.size()));
                break;
            case "GetEmployeePayrolls":
                _managerHelper.getEmployeePayrolls(cmd.subList(1,cmd.size()));
                break;
            case "ScheduleEmployees":
                _managerHelper.getEmployeeSchedule(cmd.subList(1,cmd.size()));
                break;
            case "AddGasStationInventory":
            	_managerHelper.addGasStationInventory(cmd.subList(1,cmd.size()));
            	break;
            case "RemoveGasStationInventory":
            	_managerHelper.removeGasStationInventory(cmd.subList(1,cmd.size()));
            	break;
            case "RestockInventory":
            	_managerHelper.restockGasStationInventory(cmd.subList(1,cmd.size()));
            	break;
            default:
                break;
        }
    }
}
