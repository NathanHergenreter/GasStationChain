package gasChain.service;

import gasChain.entity.Warehouse;
import gasChain.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService extends GenericService<Warehouse, Long, WarehouseRepository> {

    WarehouseService(WarehouseRepository warehouseRepository) {
        super(warehouseRepository);
    }

    public Warehouse findByName(String name) {
        return getRepository().findByName(name);
    }
}
