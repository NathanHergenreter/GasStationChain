package gasChain.repository;

import gasChain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.GasStation;

public interface GasStationRepository extends JpaRepository<GasStation, Long> {
    GasStation findByManager(Manager m);

	GasStation findByLocation(String location);
}
