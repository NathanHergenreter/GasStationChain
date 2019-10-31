package gasChain.repository;

import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GasStationInventoryRepository extends InventoryRepository<GasStationInventory, Long> {

    Set<GasStationInventory> findByGasStation(GasStation gasStation);
}
