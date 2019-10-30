package gasChain.repository;

import gasChain.entity.Corporate;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends EmployeeRepository<Corporate> {

}
