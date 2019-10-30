package gasChain.repository;

import gasChain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.GasStation;
import org.springframework.data.jpa.repository.Query;

public interface GasStationRepository extends JpaRepository<GasStation, Long> {
    GasStation findByManager(Manager m);
}
