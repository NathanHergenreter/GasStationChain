package gasChain.repository;

import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;

import java.util.Set;

public interface GasStationInventoryRepository extends InventoryRepository<GasStationInventory, Long> {

	Set<GasStationInventory> findByGasStation(GasStation gasStation);
	
	
}
