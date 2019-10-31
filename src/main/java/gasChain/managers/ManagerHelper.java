package gasChain.managers;

import gasChain.coreInterfaces.managers.IManagerHelper;
import gasChain.entity.*;
import gasChain.service.CashierService;
import gasChain.service.GasStationService;
import gasChain.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerHelper implements IManagerHelper {
    public ManagerHelper(Manager user){
        _user = user;
    }

    Manager _user;

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
    args should be ordered: username, password, name, wagesHourly
     */
    @Override
    public void updateCashier(List<String> args) throws Exception{
        //TODO: add punchIn() to CashierController
        if (args == null)
            throw new Exception("cmd 'updateCashier' does not have the proper number of args (4)");
        if(args.size()!=4)
            throw new Exception("cmd 'updateCashier' does not have the proper number of args (4)");

        Cashier model = (Cashier) _cashierService.findByUsername(args.get(0));

        model.setUsername(args.get(0));
        model.setPassword(args.get(1));
        model.setName(args.get(2));
        model.setWagesHourly(Integer.parseInt(args.get(3)));

        _cashierService.save(model);
    }

    /*
    args should be given as '-'<username> (' -')-delimited list <startHour>'>'<endHour> or '0' for no available hours
     */
    @Override
    public void updateCashierAvailability(List<String> args) throws Exception {
        if (args == null)
            throw new Exception("cmd 'updateCashierAvailability' does not have the proper number of args (7)");
        if (args.size()!=8)
            throw new Exception("cmd 'updateCashierAvailability' does not have the proper number of args (7)");

        List<List<String>> hours = new ArrayList<List<String>>();
        for(int i=1;i<args.size();i++){
            List<String> nums = Arrays.asList(args.get(i).split(">"));
            hours.add(nums);
        }

        Cashier cashier = (Cashier) _cashierService.findByUsername(args.get(0));
        Availability availability = new Availability(
                cashier,  //cashier to update
                Integer.parseInt(hours.get(0).get(0)), Integer.parseInt(hours.get(0).get(1)),//Sunday hours
                Integer.parseInt(hours.get(1).get(0)), Integer.parseInt(hours.get(1).get(1)),//Monday hours
                Integer.parseInt(hours.get(2).get(0)), Integer.parseInt(hours.get(2).get(1)),//Tuesday hours
                Integer.parseInt(hours.get(0).get(0)), Integer.parseInt(hours.get(0).get(0)),//Wednesday hours
                Integer.parseInt(hours.get(0).get(0)), Integer.parseInt(hours.get(0).get(0)),//Thursday hours
                Integer.parseInt(hours.get(0).get(0)), Integer.parseInt(hours.get(0).get(0)),//Friday hours
                Integer.parseInt(hours.get(0).get(0)), Integer.parseInt(hours.get(0).get(0))//Saturday hours
        );

        cashier.setAvailability(availability);
        _cashierService.save(cashier);
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
            throw new Exception("cmd 'removeCashier': given username does not exist");
        }
    }

    /*
    args should be given as -<username> -<startDate> -<endDate>
     */
    //TODO: handle return data in controller
    @Override
    public String getCashierPayroll(List<String> args) throws Exception {
        if (args == null)
            throw new Exception("cmd 'GetCashierPayroll': does not have the proper number of args (3)");
        if (args.size()!=3)
            throw new Exception("cmd 'GetCashierPayroll': does not have the proper number of args (3)");

        Cashier cashier = (Cashier)_cashierService.findByUsername(args.get(0));
        Date startDate = Date.valueOf(args.get(1));
        Date endDate = Date.valueOf(args.get(2));

        if (startDate.compareTo(endDate)<=0)
            throw new Exception("cmd 'GetCashierPayroll': end-date must occur after start-date");

        List<WorkPeriod> allWorkPeriods = cashier.getWorkPeriods();
        List<WorkPeriod> workPeriods = new ArrayList<>();
        for(int i=0;i<allWorkPeriods.size();i++){
            if(allWorkPeriods.get(i).getDate().compareTo(startDate) >= 0)
                workPeriods.add(allWorkPeriods.get(i));
        }

        int totalHoursWorked=0;
        for(int i=0;i<workPeriods.size();i++){
            int startTime = workPeriods.get(i).getStartHour();
            int endTime = workPeriods.get(i).getEndHour();
            if (startTime > endTime)
                totalHoursWorked += (24-startTime)+endTime;
            else
                totalHoursWorked += endTime - startTime;
        }

        return "Employee: "+cashier.getName()+" Payroll: "+(totalHoursWorked*cashier.getWagesHourly())+"\n";
    }

    /*
    args: -<startDate> -<endDate>
     */
    @Override
    public String getEmployeePayrolls(List<String> args) throws Exception {
        if (args == null)
            throw new Exception("cmd 'GetCashierPayroll': does not have the proper number of args (2)");
        if (args.size()!=2)
            throw new Exception("cmd 'GetCashierPayroll': does not have the proper number of args (2)");

        String result = "";
        List<Cashier> cashiers = _user.getStore().getCashiers();
        for(int i=0;i<cashiers.size();i++){
            List<String> newArgs = new ArrayList<>();
            newArgs.add(cashiers.get(i).getUsername());
            newArgs.add(args.get(0));
            newArgs.add(args.get(1));
            result += getCashierPayroll(newArgs);
        }
        return result;
    }

    @Override
    public String getEmployeeSchedule(List<String> args) {
        //TODO: implement
        return null;
    }
}
