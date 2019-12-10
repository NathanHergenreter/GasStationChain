package gasChain.repository;

import gasChain.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {

    List<Tax> findAll();

    Tax findByType(String type);

}
