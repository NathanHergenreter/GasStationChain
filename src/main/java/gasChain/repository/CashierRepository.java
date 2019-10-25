package gasChain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gasChain.entity.Cashier;
import gasChain.entity.GasStation;

@Repository
public interface CashierRepository extends EmployeeRepository<Cashier> {
	
	List<Cashier> findByWorkplace(GasStation workplace);
}
