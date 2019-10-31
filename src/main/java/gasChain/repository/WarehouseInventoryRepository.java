package gasChain.repository;

import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WarehouseInventoryRepository extends InventoryRepository<WarehouseInventory, Long> {

    Set<WarehouseInventory> findByWarehouse(Warehouse warehouse);
}
