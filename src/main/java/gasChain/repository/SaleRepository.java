package gasChain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.GasStation;
import gasChain.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	List<Sale> findBySellLocation(GasStation sellLocation);
}
