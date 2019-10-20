package gasChain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.Employee;
import gasChain.entity.GasStation;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	List<Employee> findByWorkplace(GasStation workplace);
}
