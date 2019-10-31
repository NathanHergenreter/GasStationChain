package gasChain.userControllers;

import gasChain.coreInterfaces.managers.IManagerHelper;
import gasChain.coreInterfaces.userControllers.IUserController;
import gasChain.entity.Employee;

import java.util.List;

public class ManagerController implements IUserController {
    public ManagerController(IManagerHelper helper){
        _managerHelper = helper;
    }

    IManagerHelper _managerHelper;

    @Override
    public void execute(List<String> cmd) throws Exception {
        String command = cmd.get(0);
        switch (command){
            case "AddCashier":
                _managerHelper.addCashier(cmd.subList(1,cmd.size()));
                break;
            case "UpdateCashier":
                _managerHelper.updateCashier(cmd.subList(1,cmd.size()));
            case "RemoveCashier":
                _managerHelper.removeCashier(cmd.subList(1,cmd.size()));
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
            default:
                break;
        }
    }
}
