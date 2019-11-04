package gasChain.service;

import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;
import gasChain.entity.Item;
import gasChain.entity.WarehouseInventory;
import gasChain.repository.GasStationInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class GasStationInventoryService
		extends InventoryService<GasStationInventory, Long, GasStationInventoryRepository> {

	@Autowired
	public GasStationInventoryService(GasStationInventoryRepository repo) {
		super(repo);
	}

	@Override
	public GasStationInventoryRepository getRepository() {
		return super.getRepository();
	}

	public Set<GasStationInventory> findByGasStation(GasStation gasStation) {
		return getRepository().findByGasStation(gasStation);
	}

	public GasStationInventory findGasStationInventoriesByGasStationAndAndItem(GasStation gasStation, Item item) {
		return getRepository().findGasStationInventoriesByGasStationAndAndItem(gasStation, item);
	}

	public GasStationInventory RemoveItemFromInventory(GasStation gasStation, Item item) {
		GasStationInventory gasStationInventory = getRepository().findGasStationInventoriesByGasStationAndAndItem(gasStation, item);
		gasStationInventory.setQuantity(gasStationInventory.getQuantity() - 1);
		getRepository().save(gasStationInventory);
		return gasStationInventory;
	}

    /*
     * For now this is going to be a 1 click option to restock an inventory for a given gas station
     * For each item in the gas station's inventory we check if there are half or less and restock if that's the case
     * We check all of the warehouses that have it till we find one that has enough to fully restock the item
     * As it stands, the item teleports instantly to and from each location 
     * Could add functionality for sorting by closest location later on but that might be a little beyond scope
     * In addition to that we could make it so that it doesn't require the warehouse to have enough to fully restock
     * 
     * trying to decide whether or not to keep a log of what got restocked, probably going to
     */
    public void restockGasStation(GasStation gasStation) {
    	List<GasStationInventory> inventory = gasStation.getInventory();
    	//Set<GasStationInventory> inventory = findByGasStation(gasStation);
    	Iterator<GasStationInventory> iter = inventory.iterator();
    	//Set<Item> items;
    	
    	while(iter.hasNext()) {
    		GasStationInventory item = iter.next();
    		if((item.getQuantity() / item.getMaxQuantity()) <= .5) { // Less than half... Restock it
    			int desiredQuantity = item.getMaxQuantity() - item.getQuantity();
    			Item i = item.getItem();
    			Set<WarehouseInventory> warehouseInventory = i.getInWarehouses();
    			Iterator<WarehouseInventory> warehouseIterator = warehouseInventory.iterator();
    			while(warehouseIterator.hasNext()) {
    				WarehouseInventory warehouseItem = warehouseIterator.next();
    				if(warehouseItem.getItem().equals(i) && warehouseItem.getQuantity() >= desiredQuantity) {
    					warehouseItem.setQuantity(warehouseItem.getQuantity() - desiredQuantity);
    					// This is where I would possibly add functionality for creating a shipment object 
    					// that tracks all of the items going to what location and such but for now the 
    					// items are going to magically teleport to the given store location
    					item.setQuantity(item.getQuantity() + desiredQuantity);
    					//items.add
    					break;
    				}
    			}
    		}
    	}
    	//return items;
    }
}
