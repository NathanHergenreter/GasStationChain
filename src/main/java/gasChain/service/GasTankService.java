package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.GasTank;
import gasChain.entity.GasStation;
import gasChain.repository.GasTankRepository;

@Service
public class GasTankService extends GenericService<GasTank, Long, GasTankRepository> {

    @Autowired
	public GasTankService(GasTankRepository r) {
		super(r);
	}
    
    public GasTank findByGasStation(GasStation gasStation) {
    	return getRepository().findByGasStation(gasStation);
    }
}
