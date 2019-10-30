package gasChain.service;

import gasChain.entity.GasStation;
import gasChain.repository.GasStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GasStationService extends GenericService<GasStation, Long, GasStationRepository> {

    @Autowired
    public GasStationService(GasStationRepository gasStationRepository) {
        super(gasStationRepository);
    }

}
