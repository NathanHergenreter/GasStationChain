package gasChain.repository;

import gasChain.entity.WorkPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPeriodRepository extends JpaRepository<WorkPeriod, Long> {
}
