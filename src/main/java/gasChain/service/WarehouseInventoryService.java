package gasChain.service;

import gasChain.entity.Item;
import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.repository.WarehouseInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class WarehouseInventoryService extends InventoryService<WarehouseInventory, Long, WarehouseInventoryRepository> {

    @Autowired
    public WarehouseInventoryService(WarehouseInventoryRepository repo) {
        super(repo);
    }

    @Override
    public WarehouseInventoryRepository getRepository() {
        return super.getRepository();
    }

    public Set<WarehouseInventory> findByWarehouse(Warehouse warehouse) {
        return getRepository().findByWarehouse(warehouse);
    }
    
    /*
     * Given a list of items and quantities. Stocks the given warehouse with these items.
     */
    public void stockWarehouse(Warehouse warehouse, List<Item> items, List<Integer> quantity) {
    	Set<WarehouseInventory> inventory = findByWarehouse(warehouse);
    	Iterator<WarehouseInventory> iter = inventory.iterator();
    	for (int i = 0; i < items.size(); i++) {
    		while (iter.hasNext()) {
    			WarehouseInventory item = iter.next();
    			if (item.getItem().equals(items.get(i))) {
    				item.setQuantity(item.getQuantity() + quantity.get(i));
    			}
    		}
    	}
    }
}
