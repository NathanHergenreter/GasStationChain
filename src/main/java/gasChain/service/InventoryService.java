package gasChain.service;

import gasChain.entity.Inventory;
import gasChain.entity.Item;
import gasChain.repository.InventoryRepository;

import java.io.Serializable;

public abstract class InventoryService<T extends Inventory, ID extends Serializable, R extends InventoryRepository<T, ID>>
		extends GenericService<T, ID, R> {

	public InventoryService(R r) {
		super(r);
	}

	public T findByItem(Item item) {
		return getRepository().findByItem(item);
	}

}