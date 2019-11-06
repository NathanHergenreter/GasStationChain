package gasChain.application.controller;

import gasChain.application.manager.interfaces.ICashierHelper;
import gasChain.application.controller.interfaces.IUserController;

import java.util.List;

public class CashierController implements IUserController{

    private ICashierHelper _cashierHelper;

    public CashierController(ICashierHelper helper){
        _cashierHelper = helper;
    }

    @Override
    public void execute(List<String> cmd) throws Exception {
        String command = cmd.get(0);
        switch (command){
            case "AddWorkPeriod":
                _cashierHelper.addWorkPeriod(cmd.subList(1,cmd.size()));
                break;
            case "NewSale":
                _cashierHelper.processSale();
                break;
            case "ReturnItems":
                _cashierHelper.processReturn(cmd.subList(1, cmd.size()));
            default:
                break;
        }
    }
}
