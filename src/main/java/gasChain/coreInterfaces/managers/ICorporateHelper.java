package gasChain.coreInterfaces.managers;

import java.util.List;

/*
 * Pretty barren right now but most of the functionality for corporate will be added during the second and third iteration
 */
public interface ICorporateHelper extends IUserHelper {

	void addManager(List<String> args) throws Exception;
	void removeManager(List<String> args) throws Exception;
	void addGasStation(List<String> args) throws Exception;
	void removeGasStation(List<String> args) throws Exception;
	int restockWarehouseInventory(List<String> args) throws Exception;
	
}