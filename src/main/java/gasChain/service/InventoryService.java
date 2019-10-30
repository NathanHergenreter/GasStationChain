package gasChain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gasChain.entity.Inventory;
import gasChain.entity.Item;
import gasChain.repository.InventoryRepository;

@Service
public abstract class InventoryService {
	
	protected InventoryRepository repo;
	
	public InventoryService(InventoryRepository repo) { this.repo = repo; }
	
	protected InventoryRepository repo() { return repo; }
	
	public InventoryService add(Inventory inventory) { repo.save(inventory); return this; }
	
	public InventoryService update(Inventory inventory) { repo.save(inventory); return this; }
	
	public boolean isEmpty() { return repo.count() <= 0; }
	
	public List<Inventory> findAll() { return repo.findAll(); }
	
	public List<Inventory> findByItem(Item item) { return repo.findByItem(item); }
}
