package gasChain.repository;

import gasChain.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Warehouse findByName(String name);
}
