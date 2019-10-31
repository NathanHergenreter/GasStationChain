package gasChain.coreInterfaces.corporate;

import java.util.List;

/*
 * Pretty barren right now but most of the functionality for corporate will be added during the second and third iteration
 */
public interface ICorporateHelper {

	int restockWarehouseInventory(String name, List<String> items, List<Integer> quantities);
	
}