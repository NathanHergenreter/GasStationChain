package gasChain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;
import gasChain.repository.GasStationInventoryRepository;

@Service
public class GasStationInventoryService extends InventoryService {
	
	@Autowired
	public GasStationInventoryService(GasStationInventoryRepository repo) { super(repo); }
	
	@Override
	protected GasStationInventoryRepository repo() { return (GasStationInventoryRepository) repo; }
	
	public List<GasStationInventory> findByGasStation(GasStation gasStation) { return repo().findByGasStation(gasStation); }

}
