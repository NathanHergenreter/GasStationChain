package gasChain.repository;

import gasChain.entity.Cashier;
import gasChain.entity.GasStation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashierRepository extends EmployeeRepository<Cashier> {

    List<Cashier> findByWorkplace(GasStation workplace);
}
