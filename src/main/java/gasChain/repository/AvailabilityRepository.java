package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gasChain.entity.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

}
