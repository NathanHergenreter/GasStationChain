package gasChain.service;

import gasChain.entity.GasStation;
import gasChain.entity.Item;
import gasChain.entity.Promotion;
import gasChain.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class PromotionService extends GenericService<Promotion, Long, PromotionRepository> {

    @Autowired
    PromotionService(PromotionRepository promotionRepository) {
        super(promotionRepository);
    }

    public Promotion findByItem(Item item) {
        return getRepository().findByItem(item);
    }

    public Set<Promotion> findByGasStation(GasStation gasStation) {
        return getRepository().findByGasStation(gasStation);
    }

    public Set<Promotion> findAllPromotionsInTimePeriod(Date startDate, Date endDate, GasStation gasStation) {
        return getRepository().findAllByStartDateAfterAndStartDateBeforeAndGasStation(startDate, endDate, gasStation);
    }

    public Promotion findPromotionByGasStationAndItem(GasStation gasStation, Item item) {
        return getRepository().findPromotionByGasStationAndItem(gasStation, item);
    }

    public boolean existsPromotion(Item item) {
        return findByItem(item) != null;
    }
}
