package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import gasChain.entity.User;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

//	@Query("select u from #{#entityName} as u where u.email = ?1 ") // Needed?
	public T findByUsername(String username);
}
