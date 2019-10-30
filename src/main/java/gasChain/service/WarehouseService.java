package gasChain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Warehouse;
import gasChain.repository.WarehouseRepository;

@Service
public class WarehouseService {

	@Autowired
	WarehouseRepository repo;
	
	public WarehouseService add(Warehouse warehouse) { repo.save(warehouse); return this; }
	
	public WarehouseService update(Warehouse warehouse) { repo.save(warehouse); return this; }
	
	public boolean isEmpty() { return repo.count() <= 0; }
	
	public List<Warehouse> findAll() { return repo.findAll(); }
	
	public Warehouse findByName(String name) { return repo.findByName(name); }
}
