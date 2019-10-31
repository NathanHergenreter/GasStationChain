package gasChain.coreInterfaces.managers;

import java.util.List;

public interface IManagerHelper {
    void addCashier(List<String> args);
    void removeCashier(List<String> args);
    void updateCashierHours(List<String> args);
    void addItem(List<String> args);
    boolean restockGasStationInventory(List<String> args);
    String getCashierPayroll(List<String> args);
    String getEmployeePayrolls(List<String> args);
    String getEmployeeSchedule(List<String> args);
}
