package gasChain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.GasStation;
import gasChain.repository.GasStationRepository;

@Service
public class GasStationService {

    @Autowired
    private GasStationRepository repo;
    
    public GasStationService add(GasStation gasStation) { repo.save(gasStation); return this; }
    
    public boolean isEmpty() { return repo.count() <= 0; }
    
    public List<GasStation> findAll() { return repo.findAll(); }
    
}
