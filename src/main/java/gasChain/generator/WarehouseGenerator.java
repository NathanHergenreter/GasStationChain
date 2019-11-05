package gasChain.generator;

import java.util.ArrayList;

import gasChain.entity.Item;
import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.service.ServiceMaster;

public class WarehouseGenerator {

	private ServiceMaster service;
	private GenDataRepository repo;
	
	public WarehouseGenerator(ServiceMaster service, GenDataRepository repo) 
	{ 
		this.service = service; this.repo = repo; 
	}

	public void execute()
	{
		int numWarehouses = 30;
		generateWarehouses(numWarehouses);
	}

	private void generateWarehouses(int num)
	{
		ArrayList<Warehouse> warehouses = repo.produceWarehouses(num);
		
		for(Warehouse warehouse : warehouses)
		{
			service.warehouse().save(warehouse);
			
			ArrayList<WarehouseInventory> inventory = generateWarehouseInventory();
			for(WarehouseInventory item : inventory)
			{
				item.setWarehouse(warehouse);
				service.warehouseInventory().save(item);
				warehouse.addInventory(item);
				
			}
		}
	}
	
	private ArrayList<WarehouseInventory> generateWarehouseInventory()
	{
		ArrayList<WarehouseInventory> inventory = new ArrayList<WarehouseInventory>();
		ArrayList<Item> items = (ArrayList<Item>) service.item().findAll();
		
		for(int i = GenUtil.rng.nextInt(2); i < items.size(); i += GenUtil.rng.nextInt(2) )
		{
			Item item = items.get(i);
			inventory.add(new WarehouseInventory(item, item.getSuggestRetailPrice(), GenUtil.rng.nextInt(50)));
		}
		
		return inventory;
	}
}
