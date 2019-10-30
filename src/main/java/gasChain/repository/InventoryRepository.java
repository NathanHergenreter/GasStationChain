package gasChain.repository;

import gasChain.entity.Inventory;
import gasChain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface InventoryRepository<T extends Inventory, ID extends Serializable> extends JpaRepository<T, ID> {

	T findByItem(Item item);

}
