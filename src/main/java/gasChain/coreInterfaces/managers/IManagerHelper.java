package gasChain.coreInterfaces.managers;

import gasChain.entity.Employee;

import java.util.List;

public interface IManagerHelper extends IUserHelper {
    void addCashier(List<String> args) throws Exception;
    void updateCashier(List<String> args) throws Exception;
    void updateCashierAvailability(List<String> args) throws Exception;
    void removeCashier(List<String> args) throws Exception;
    String getCashierPayroll(List<String> args) throws Exception;
    String getEmployeePayrolls(List<String> args) throws Exception;
    String getEmployeeSchedule(List<String> args);
}
