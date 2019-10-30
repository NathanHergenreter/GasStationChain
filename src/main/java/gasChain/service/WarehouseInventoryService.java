package gasChain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.repository.WarehouseInventoryRepository;

@Service
public class WarehouseInventoryService extends InventoryService {

	@Autowired
	public WarehouseInventoryService(WarehouseInventoryRepository repo) { super(repo); }
	
	@Override
	protected WarehouseInventoryRepository repo() { return (WarehouseInventoryRepository) repo; }
	
	public List<WarehouseInventory> findByWarehouse(Warehouse warehouse) { return repo().findByWarehouse(warehouse); }
}
