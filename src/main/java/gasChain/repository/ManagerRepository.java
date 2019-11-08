package gasChain.repository;

import gasChain.entity.GasStation;
import gasChain.entity.Manager;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends EmployeeRepository<Manager> {

    Manager findByStore(GasStation store);
}
