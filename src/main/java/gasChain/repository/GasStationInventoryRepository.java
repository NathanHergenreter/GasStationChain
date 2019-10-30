package gasChain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;
import gasChain.entity.Item;

public interface GasStationInventoryRepository extends InventoryRepository<GasStationInventory> {

	List<GasStationInventory> findByGasStation(GasStation gasStation);
}
