package gasChain.repository;

import gasChain.entity.Corporate;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends EmployeeRepository<Corporate> {

	List<Corporate> findAll();
}
