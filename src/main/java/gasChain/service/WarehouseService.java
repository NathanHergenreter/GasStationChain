package gasChain.service;

import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService extends GenericService<Warehouse, Long, WarehouseRepository> {

    WarehouseService(WarehouseRepository warehouseRepository) {
        super(warehouseRepository);
    }

    public Warehouse findByLocation(String location) {
        return getRepository().findByLocation(location);
    }

    public boolean existsLocation(String location) {
        return getRepository().findByLocation(location) != null;
    }

    public Warehouse removeInventory(Warehouse warehouse, WarehouseInventory item) {
        Warehouse temp = getRepository().findByLocation(warehouse.getLocation());
        temp.removeInventory(item);
        getRepository().save(temp);

        return temp;
    }
}
