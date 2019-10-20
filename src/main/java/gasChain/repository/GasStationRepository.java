package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.GasStation;

public interface GasStationRepository extends JpaRepository<GasStation, Long> {

}
