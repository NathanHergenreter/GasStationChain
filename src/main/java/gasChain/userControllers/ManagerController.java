package gasChain.userControllers;

import gasChain.coreInterfaces.managers.IManagerHelper;
import gasChain.coreInterfaces.userControllers.IUserController;
import gasChain.entity.Employee;

import java.util.List;

public class ManagerController implements IUserController {
    public ManagerController(IManagerHelper helper){
        //TODO: take in user in constructor
        _managerHelper = helper;
    }

    IManagerHelper _managerHelper;
    Employee _currentUser;

    @Override
    public void execute(List<String> cmd) {
        String command = cmd.get(0);
        //TODO: only pass in cmd e for i>=1
        switch (command){
            case "AddCashier":
                _managerHelper.addCashier(cmd);
                break;
            case "RemoveCashier":
                _managerHelper.removeCashier(cmd);
                break;
            case "UpdateCashierHours":
                _managerHelper.updateCashierHours(cmd);
                break;
            case "GetCashierPayroll":
                _managerHelper.getCashierPayroll(cmd);
                break;
            case "GetEmployeePayrolls":
                _managerHelper.getEmployeePayrolls(cmd);
                break;
            case "ScheduleEmployees":
                _managerHelper.getEmployeeSchedule(cmd);
                break;
            default:
                break;
        }
    }
}
