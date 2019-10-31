package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Availability;
import gasChain.repository.AvailabilityRepository;

@Service
public class AvailabilityService extends GenericService<Availability, Long, AvailabilityRepository> {

	@Autowired
	public AvailabilityService(AvailabilityRepository r) {
		super(r);
	}

}
