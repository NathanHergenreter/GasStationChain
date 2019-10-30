package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

	Warehouse findByName(String name);
}
