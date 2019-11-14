package gasChain.service;

import gasChain.entity.Item;
import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.repository.WarehouseInventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WarehouseInventoryService
        extends InventoryService<WarehouseInventory, Long, WarehouseInventoryRepository> {

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

    public WarehouseInventory findWarehouseInventoriesByWarehouseAndAndItem(Warehouse warehouse, Item item) {
        return getRepository().findWarehouseInventoriesByWarehouseAndItem(warehouse, item);
    }
}
