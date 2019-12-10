package gasChain.service;

import gasChain.entity.GasStation;
import gasChain.entity.GasTankInventory;
import gasChain.entity.Item;
import gasChain.repository.GasTankInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

//@Service
//public class GasTankInventoryService
//        extends InventoryService<GasTankInventory, Long, GasTankInventoryRepository> {
//
//    @Autowired
//    public GasTankInventoryService(GasTankInventoryRepository repo) {
//        super(repo);
//    }
//
//    @Override
//    public GasTankInventoryRepository getRepository() {
//        return super.getRepository();
//    }
//
//    public Set<GasTankInventory> findByGasTank(GasStation gasTank) {
//        return getRepository().findByGasStation(gasTank);
//    }
//
//    public GasTankInventory findGasTankInventoryByGasTankAndItem(GasStation gasStation, Item item) {
//        return getRepository().findGasTankInventoryByGasStationAndItem(gasStation, item);
//    }
//
//    public GasTankInventory RemoveItemFromInventory(GasStation gasStation, Item item) {
//        GasTankInventory gasTankInventory = getRepository().findGasTankInventoryByGasStationAndItem(gasStation, item);
//        gasTankInventory.setQuantity(gasTankInventory.getQuantity() - 1);
//        return gasTankInventory;
//    }
//}

public class GasTankInventoryService {}