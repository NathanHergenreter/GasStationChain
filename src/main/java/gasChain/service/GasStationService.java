package gasChain.service;

import gasChain.entity.GasStation;
import gasChain.entity.Manager;
import gasChain.repository.GasStationRepository;
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
}
