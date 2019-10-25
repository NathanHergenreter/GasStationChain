package gasChain.repository;

import org.springframework.stereotype.Repository;

import gasChain.entity.Manager;

@Repository
public interface ManagerRepository extends EmployeeRepository<Manager> {

}
