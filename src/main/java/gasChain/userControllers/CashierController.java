package gasChain.userControllers;

import java.util.List;

import gasChain.coreInterfaces.userControllers.IUserController;
import gasChain.coreInterfaces.managers.ICashierHelper;

public class CashierController implements IUserController{
    public CashierController(ICashierHelper helper){
        _cashierHelper = helper;
    }

    private ICashierHelper _cashierHelper;

    @Override
    public void execute(List<String> cmd) {
        String command = cmd.get(0);
        switch (command){
            case "AddWorkPeriod":
                _cashierHelper.addWorkPeriod(cmd.subList(1,cmd.size()));
                break;
            default:
                break;
        }
    }
}
