package gasChain.service;

import gasChain.entity.GasStation;
import gasChain.entity.Item;
import gasChain.entity.Promotion;
import gasChain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ItemService extends GenericService<Item, Long, ItemRepository> {

    @Autowired
    ItemService(ItemRepository itemRepository) {
        super(itemRepository);
    }

    public Item findByName(String name) {
        return getRepository().findByName(name);
    }
    
    public boolean existsItem(String name) {
    	return findByName(name) != null;
    }

}
