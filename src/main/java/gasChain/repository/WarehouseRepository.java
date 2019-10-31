package gasChain.repository;

import gasChain.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

	Warehouse findByName(String name);

	Warehouse findByLocation(String location);
	
	Warehouse findByPhoneNumber(String phoneNumber);
}
