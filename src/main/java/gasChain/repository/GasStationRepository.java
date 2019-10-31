package gasChain.repository;

import gasChain.entity.GasStation;
import gasChain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {

    GasStation findByManager(Manager m);

	GasStation findByLocation(String location);
}
