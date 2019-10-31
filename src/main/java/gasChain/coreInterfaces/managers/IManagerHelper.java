package gasChain.coreInterfaces.managers;

import gasChain.entity.Employee;

import java.util.List;

public interface IManagerHelper {
    void addCashier(List<String> args) throws Exception;
    void updateCashier(List<String> args) throws Exception;
    void removeCashier(List<String> args) throws Exception;
    String getCashierPayroll(List<String> args);
    String getEmployeePayrolls(List<String> args);
    String getEmployeeSchedule(List<String> args);
}
