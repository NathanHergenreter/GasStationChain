package gasChain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import gasChain.entity.GasStationInventory;
import gasChain.entity.Inventory;
import gasChain.entity.Item;

@NoRepositoryBean
public interface InventoryRepository<T extends Inventory> extends JpaRepository<T, Long> {
	
	List<GasStationInventory> findByItem(Item item);
}
