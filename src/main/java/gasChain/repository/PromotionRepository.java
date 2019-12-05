package gasChain.repository;

import gasChain.entity.GasStation;
import gasChain.entity.Item;
import gasChain.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Promotion findByItem(Item item);

    Set<Promotion> findByGasStation(GasStation gasStation);

    Set<Promotion> findAllByStartDateAfterAndStartDateBeforeAndGasStation(Date startDate, Date endDate, GasStation gasStation);

    Promotion findPromotionByGasStationAndItem(GasStation gasStation, Item item);
}