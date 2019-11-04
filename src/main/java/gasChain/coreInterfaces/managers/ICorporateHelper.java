package gasChain.coreInterfaces.managers;

import java.util.List;

/*
 * Pretty barren right now but most of the functionality for corporate will be added during the second and third iteration
 */
public interface ICorporateHelper extends IUserHelper {

	int restockWarehouseInventory(List<String> args);
	
}