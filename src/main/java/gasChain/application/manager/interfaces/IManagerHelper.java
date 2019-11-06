package gasChain.application.manager.interfaces;

import java.util.List;

public interface IManagerHelper extends IUserHelper {
    void addCashier(List<String> args) throws Exception;
    void updateCashier(List<String> args) throws Exception;
    void updateCashierAvailability(List<String> args) throws Exception;
    void removeCashier(List<String> args) throws Exception;
    void listStoreCashiers();
    void getCashierPayroll(List<String> args) throws Exception;
    void getEmployeePayrolls(List<String> args) throws Exception;
    void getEmployeeSchedule(List<String> args);
    void addGasStationInventory(List<String> args) throws Exception;
    void removeGasStationInventory(List<String> args) throws Exception;
    boolean restockGasStationInventory(List<String> args) throws Exception;
    void addItem(List<String> args);
}
