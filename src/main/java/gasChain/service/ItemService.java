package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Item;
import gasChain.repository.ItemRepository;

@Service
public class ItemService extends GenericService<Item, Long, ItemRepository> {

	@Autowired
	ItemService(ItemRepository itemRepository) {
		super(itemRepository);
	}
	
	public Item findByName(String name) { return getRepository().findByName(name); }
}
