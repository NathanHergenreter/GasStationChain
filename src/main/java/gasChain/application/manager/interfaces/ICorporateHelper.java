package gasChain.application.manager.interfaces;

import java.util.List;

public interface ICorporateHelper extends IUserHelper {

	void addManager(List<String> args) throws Exception;
	void removeManager(List<String> args) throws Exception;
	void addGasStation(List<String> args) throws Exception;
	void removeGasStation(List<String> args) throws Exception;
	void addWarehouse(List<String> args) throws Exception;
	void removeWarehouse(List<String> args) throws Exception;
	void addWarehouseInventory(List<String> args) throws Exception;
	void removeWarehouseInventory(List<String> args) throws Exception;
	int restockWarehouseInventory(List<String> args) throws Exception;
	
}