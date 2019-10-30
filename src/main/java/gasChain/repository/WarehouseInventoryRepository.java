package gasChain.repository;

import java.util.List;

import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;

public interface WarehouseInventoryRepository extends InventoryRepository<WarehouseInventory> {

	List<WarehouseInventory> findByWarehouse(Warehouse warehouse);
}
