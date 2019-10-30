package gasChain.repository;

import gasChain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.GasStation;
import org.springframework.data.jpa.repository.Query;

public interface GasStationRepository extends JpaRepository<GasStation, Long> {
    @Query("select station from GasStation station where station.manager.username = ?0")
    GasStation findByManagerUsername(String username);//TODO make sure query is valid
}
