package gasChain.managers;

import gasChain.coreInterfaces.managers.ICorporateHelper;
import gasChain.entity.Corporate;
import gasChain.entity.Item;
import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.service.GasStationService;
import gasChain.service.ItemService;
import gasChain.service.CorporateService;
import gasChain.service.WarehouseInventoryService;
import gasChain.service.WarehouseService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class CorporateHelper implements ICorporateHelper {
	
	public CorporateHelper(Corporate employee) {
		this.user = employee;
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
	@Autowired
	ItemService itemService;
	
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
	public int restockWarehouseInventory(List<String> args)
	{
		String name = args.get(0);
		Warehouse warehouse = warehouseService.findByName(name);
    	List<String> items = new ArrayList<String>();
    	List<Integer> quantities = new ArrayList<Integer>();
    	
    	for(int idx = 1; idx < args.size() - 1; idx += 2)
    	{
    		items.add(args.get(idx));
    		quantities.add(new Integer(args.get(idx + 1)));
    	}
    	
    	return restockWarehouseInventory2(warehouse, items, quantities);
	}
	
	public int restockWarehouseInventory2(Warehouse warehouse, List<String> items, List<Integer> quantities) {
		Set<WarehouseInventory> warehouseInventory = warehouseInventoryService.findByWarehouse(warehouse);
		int total = 0;
		
		for (int restockIdx = 0; restockIdx < items.size(); restockIdx++) 
		{
			String item = items.get(restockIdx);
			int quantity = quantities.get(restockIdx);
			Iterator<WarehouseInventory> iter = warehouseInventory.iterator();

			while (iter.hasNext()) 
			{
				WarehouseInventory inventoryItem = iter.next();
				if (inventoryItem.ofItem(item)) 
				{
					total += quantity * inventoryItem.getPrice();
					inventoryItem.setQuantity(inventoryItem.getQuantity() + quantity);
					
					warehouseInventoryService.save(inventoryItem); 
					break;
				}
				
				// Item isn't in warehouse, add new WarehouseInventory to warehouse
				if (!iter.hasNext()) 
				{
					Item itemType = itemService.findByName(item);
					WarehouseInventory newInventory = new WarehouseInventory(itemType, 
							itemType.getSuggestRetailPrice(), quantity);
					newInventory.setWarehouse(warehouse);
					warehouse.addInventory(newInventory);
					warehouseInventoryService.save(newInventory); 
				}
			}
			
		}
		return total;
	}

}
