package gasChain.service;

import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;
import gasChain.entity.Item;
import gasChain.repository.GasStationInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GasStationInventoryService
        extends InventoryService<GasStationInventory, Long, GasStationInventoryRepository> {

    @Autowired
    public GasStationInventoryService(GasStationInventoryRepository repo) {
        super(repo);
    }

    @Override
    public GasStationInventoryRepository getRepository() {
        return super.getRepository();
    }

    public Set<GasStationInventory> findByGasStation(GasStation gasStation) {
        return getRepository().findByGasStation(gasStation);
    }

    public GasStationInventory findGasStationInventoryByGasStationAndItem(GasStation gasStation, Item item) {
        return getRepository().findGasStationInventoryByGasStationAndItem(gasStation, item);
    }

    public GasStationInventory RemoveItemFromInventory(GasStation gasStation, Item item) {
        GasStationInventory gasStationInventory = getRepository().findGasStationInventoryByGasStationAndItem(gasStation, item);
        gasStationInventory.setQuantity(gasStationInventory.getQuantity() - 1);
        return gasStationInventory;
    }
}
