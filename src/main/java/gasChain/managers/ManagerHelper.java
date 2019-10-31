package gasChain.managers;

import gasChain.coreInterfaces.managers.IManagerHelper;
import gasChain.entity.Cashier;
import gasChain.entity.Employee;
import gasChain.service.CashierService;
import gasChain.service.GasStationService;
import gasChain.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ManagerHelper implements IManagerHelper {
    public ManagerHelper(Employee user){
        _user = user;
    }

    Employee _user;

    @Autowired
    GasStationService _gasStationService;
    @Autowired
    CashierService _cashierService;


    /*
    args should be ordered: username, password, name, wagesHourly, hoursWeekly
     */
    @Override
    public void addCashier(List<String> args) throws Exception{
        if(args.size()!=5){
            throw new Exception("cmd 'addCashier' does not have the proper number of args (5)");
        }
        Cashier model = new Cashier(
                args.get(0),
                args.get(1),
                args.get(2),
                Integer.parseInt(args.get(3)),
                Integer.parseInt(args.get(3))
        );
        _cashierService.save(model);
    }

    /*
    args should be ordered: username, password, name, wagesHourly, hoursWeekly
     */
    @Override
    public void updateCashier(List<String> args) throws Exception{
        //TODO: add punchIn() to CashierController
        if(args.size()!=5){
            throw new Exception("cmd 'addCashier' does not have the proper number of args (5)");
        }
        Cashier model = (Cashier) _cashierService.findByUsername(args.get(0));

        model.setUsername(args.get(0));
        model.setPassword(args.get(1));
        model.setName(args.get(2));
        model.setWagesHourly(Integer.parseInt(args.get(3)));
        model.setHoursWeekly(Integer.parseInt(args.get(4)));

        _cashierService.save(model);
    }

    /*
    'username' only required arg
     */
    @Override
    public void removeCashier(List<String> args) throws Exception{
        if (_cashierService.existsUser(args.get(0))){
            Cashier e = (Cashier) _cashierService.findByUsername(args.get(1));
            _cashierService.deleteById(e.getId());
        }else{
            throw new Exception("cmd: 'removeCashier': given username does not exist");
        }
    }

    //TODO: handle return data in controller
    @Override
    public String getCashierPayroll(List<String> args) {
        if (_cashierService.existsUser(args.get(1))){
            Cashier e = (Cashier)_cashierService.findByUsername(args.get(1));
            int hoursWorked = e.getHoursWeekly();
            float hourlyRate = e.getWagesHourly();
            float pay = hourlyRate * hoursWorked;
            return Float.toString(pay);
        }
        return null;
    }

    @Override
    public String getEmployeePayrolls(List<String> args) {
        String payrolls = "";
        for(int i=1; i<args.size();i++){
            if (_cashierService.existsUser(args.get(i))){
                Cashier e = (Cashier)_cashierService.findByUsername(args.get(i));
                int hoursWorked = e.getHoursWeekly();
                float hourlyRate = e.getWagesHourly();
                float pay = hourlyRate * hoursWorked;

                payrolls += "Cashier: " + ((Cashier) e).getName();
                payrolls +=  "\tPay: " + Float.toString(pay) + "\n";
            }
        }
        return payrolls;
    }

    @Override
    public String getEmployeeSchedule(List<String> args) {
        //TODO: implement
        return null;
    }
}
