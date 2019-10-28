package gasChain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import gasChain.entity.GasStation;
import gasChain.entity.Manager;

@Repository
public interface ManagerRepository extends EmployeeRepository<Manager> {

	Manager findByStore(GasStation store);
}
