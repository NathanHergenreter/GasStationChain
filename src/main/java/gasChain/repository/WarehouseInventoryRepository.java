package gasChain.repository;

import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;

import java.util.Set;

public interface WarehouseInventoryRepository extends InventoryRepository<WarehouseInventory, Long> {

    Set<WarehouseInventory> findByWarehouse(Warehouse warehouse);
}
