package gasChain.service;

import gasChain.entity.Availability;
import gasChain.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService extends GenericService<Availability, Long, AvailabilityRepository> {

    @Autowired
    public AvailabilityService(AvailabilityRepository r) {
        super(r);
    }
}
