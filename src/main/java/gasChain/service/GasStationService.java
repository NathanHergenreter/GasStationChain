package gasChain.service;

import gasChain.entity.GasStation;
import gasChain.entity.Manager;
import gasChain.entity.Promotion;
import gasChain.repository.GasStationRepository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GasStationService extends GenericService<GasStation, Long, GasStationRepository> {

    @Autowired
    public GasStationService(GasStationRepository gasStationRepository) {
        super(gasStationRepository);
    }

    public GasStation findByLocation(String location) {
        return getRepository().findByLocation(location);
    }

    public GasStation findByManager(Manager manager) {
        return getRepository().findByManager(manager);
    }

    public boolean existsLocation(String location) {
        return getRepository().findByLocation(location) != null;
    }
    
    @Transactional
    public GasStation addPromotion(String location, Promotion promotion) {
    	GasStation gasStation = findByLocation(location);
    	promotion.setGasStation(gasStation);
    	gasStation.addPromotion(promotion);
    	
    	return gasStation;
    }
}
