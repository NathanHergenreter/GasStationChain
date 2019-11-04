package gasChain.managers;

import gasChain.coreInterfaces.managers.ICorporateHelper;
import gasChain.entity.Corporate;
import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.service.GasStationService;
import gasChain.service.CorporateService;
import gasChain.service.WarehouseInventoryService;
import gasChain.service.WarehouseService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class CorporateHelper implements ICorporateHelper {
	
	public CorporateHelper(Corporate user) {
		this.user = user;
	}

	Corporate user;
	
	@Autowired
	GasStationService gasStationService;
	@Autowired
	CorporateService managerService;
	@Autowired
	WarehouseInventoryService warehouseInventoryService;
	@Autowired
	WarehouseService warehouseService;
	
	
	
	/*
	 * Takes name of warehouse as argument along with a list of items and quantities indexed at 0
	 * Iterates through current warehouse database to find matching item
	 * Adds the quantities and saves to database
	 * Returns total cost of the restock
	 * (in real world this would already be payed for, might change functionality in that regard later)
	 * 
	 * (non-Javadoc)
	 * @see gasChain.coreInterfaces.corporate.ICorporateHelper#restockWarehouseInventory(java.lang.String, java.util.List, java.util.List)
	 */

	@Override
	public int restockWarehouseInventory(String name, List<String> items, List<Integer> quantities) {
		Warehouse warehouse = warehouseService.findByName(name);
		Set<WarehouseInventory> warehouseInventory = warehouseInventoryService.findByWarehouse(warehouse);
		int total = 0;
		for (int cursor = 0; cursor < items.size(); cursor++) {
			Iterator<WarehouseInventory> iter = warehouseInventory.iterator();

			while (iter.hasNext()) {
				WarehouseInventory i = iter.next();
				if (i.getItem().getName().equals(items.get(cursor))) {
					WarehouseInventory temp = i;
					total += quantities.get(cursor) * temp.getPrice();
					temp.setQuantity(i.getQuantity() + quantities.get(cursor));
					warehouseInventoryService.save(temp); 
					break;
				}
				if (!iter.hasNext()) { //item isn't in database yet
					
				}
			}
			
		}
		return total;
	}

}
