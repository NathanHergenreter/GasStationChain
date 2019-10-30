package gasChain.managers;

import gasChain.coreInterfaces.managers.IManagerHelper;
import gasChain.entity.Cashier;
import gasChain.entity.Employee;
import gasChain.service.GasStationService;
import gasChain.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ManagerHelper implements IManagerHelper {
    public ManagerHelper(){

    }

    @Autowired
    GasStationService gasStationService;
    @Autowired
    ManagerService managerService;


    @Override
    public void addCashier(List<String> args) {
        Employee model = new Cashier("","","",0,0);
        managerService.add(model);
    }

    @Override
    public void removeCashier(List<String> args) {
        if (managerService.existsUser(args.get(1))){
            Employee e = managerService.findByUsername(args.get(1));
            managerService.deleteEmployee(e);
        }
    }

    @Override
    public void updateCashierHours(List<String> args) {

    }

    @Override
    public String getCashierPayroll(List<String> args) {
        if (managerService.existsUser(args.get(1))){
            Employee e = managerService.findByUsername(args.get(1));
            int hoursWorked = ((Cashier)e).getHoursWeekly();
            float hourlyRate = ((Cashier)e).getWagesHourly();
            float pay = hourlyRate * hoursWorked;
            return Float.toString(pay);
        }
        return null;
    }

    @Override
    public String getEmployeePayrolls(List<String> args) {
        String payrolls = "";
        for(int i=1; i<args.size();i++){
            if (managerService.existsUser(args.get(i))){
                Employee e = managerService.findByUsername(args.get(i));
                int hoursWorked = ((Cashier)e).getHoursWeekly();
                float hourlyRate = ((Cashier)e).getWagesHourly();
                float pay = hourlyRate * hoursWorked;

                payrolls += "Cashier: " + ((Cashier) e).getName();
                payrolls +=  "\tPay: " + Float.toString(pay) + "\n";
            }
        }
        return payrolls;
    }

    @Override
    public String getEmployeeSchedule(List<String> args) {
        //TODO: create tables for Availability and Work Period
        return null;
    }
}
