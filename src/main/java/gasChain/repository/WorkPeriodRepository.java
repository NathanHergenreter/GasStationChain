package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gasChain.entity.WorkPeriod;

@Repository
public interface WorkPeriodRepository extends JpaRepository<WorkPeriod, Long> {
}
