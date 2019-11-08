package gasChain.repository;

import gasChain.entity.Corporate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorporateRepository extends EmployeeRepository<Corporate> {

    List<Corporate> findAll();
}
