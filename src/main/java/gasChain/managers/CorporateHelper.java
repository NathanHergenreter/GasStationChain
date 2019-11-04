package gasChain.managers;

import gasChain.coreInterfaces.managers.ICorporateHelper;
import gasChain.entity.Corporate;
import gasChain.entity.GasStation;
import gasChain.entity.Item;
import gasChain.entity.Manager;
import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.service.GasStationService;
import gasChain.service.ItemService;
import gasChain.service.CashierService;
import gasChain.service.CorporateService;
import gasChain.service.ManagerService;
import gasChain.service.WarehouseInventoryService;
import gasChain.service.WarehouseService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CorporateHelper implements ICorporateHelper {
	
	public CorporateHelper(Corporate employee) {
		this.user = employee;
	}
	
	Corporate user;
	
	private GasStationService gasStationService = ManagersAutoWire.getBean(GasStationService.class);
	private CashierService cashierService = ManagersAutoWire.getBean(CashierService.class);
	private CorporateService corporateService = ManagersAutoWire.getBean(CorporateService.class);
	private ManagerService managerService = ManagersAutoWire.getBean(ManagerService.class);
	private WarehouseInventoryService warehouseInventoryService = ManagersAutoWire.getBean(WarehouseInventoryService.class);
	private WarehouseService warehouseService = ManagersAutoWire.getBean(WarehouseService.class);
	private ItemService itemService = ManagersAutoWire.getBean(ItemService.class);
	
	@Override
	public void addManager(List<String> args) throws Exception {
        if (args == null || args.size() != 3)
            throw new Exception("Invalid number of args for 'AddManager' (3)");
		
        String username = args.get(0);
        String password = args.get(1);
        String location = args.get(2);
        GasStation store = gasStationService.findByLocation(location);
        
        if(cashierService.existsUser(username)
        || managerService.existsUser(username)
        || corporateService.existsUser(username)
          )
            throw new Exception("Username '" + username + "' taken.");
        
        if(store == null)
            throw new Exception("Location '" + location + "' does not exist.");
        
        Manager manager = new Manager(username, password);
        manager.setStore(store);
        managerService.save(manager);
        store.setManager(manager);
        gasStationService.save(store);
	}

	@Override
	public void removeManager(List<String> args) throws Exception {
        if (args == null || args.size() != 1)
            throw new Exception("Invalid number of args for 'RemoveManager' (1)");

        String username = args.get(0);
        
        if(!managerService.existsUser(username))
            throw new Exception("Manager of username '" + username + "' does not exist.");
        
        Manager manager = (Manager) managerService.findByUsername(username);
        GasStation store = manager.getStore();
        if(store != null) store.setManager(null);
        manager.setStore(null);

        if(store != null) gasStationService.save(store);
        managerService.delete(manager);
	}
	
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
	public int restockWarehouseInventory(List<String> args) throws Exception
	{
        if (args == null || args.size() < 1)
            throw new Exception("Invalid minimum number of args for 'RestockInventory' (1 + 2n)");
        if(args != null && args.size() % 2 == 0)
            throw new Exception("Invalid number of inventory args for 'RestockInventory' (Must be even)");

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
	
	public int restockWarehouseInventory2(Warehouse warehouse, List<String> items, List<Integer> quantities) 
	{
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