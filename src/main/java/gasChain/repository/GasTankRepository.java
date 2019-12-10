package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gasChain.entity.GasTank;
import gasChain.entity.GasStation;

@Repository
public interface GasTankRepository extends JpaRepository<GasTank, Long> {

	GasTank findByGasStation(GasStation gasStation);
}
