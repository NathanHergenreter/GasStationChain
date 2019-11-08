package gasChain.repository;

import gasChain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EmployeeRepository<T extends Employee> extends JpaRepository<T, Long> {

    //	@Query("select u from #{#entityName} as u where u.email = ?1 ") // Needed?
    T findByUsername(String username);
}
