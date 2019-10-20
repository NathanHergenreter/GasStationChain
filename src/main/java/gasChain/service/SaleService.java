package gasChain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Sale;
import gasChain.entity.GasStation;
import gasChain.repository.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repo;
    
    public SaleService add(Sale sale) { repo.save(sale); return this; }
    
    public boolean isEmpty() { return repo.count() <= 0; }
    
    public List<Sale> findAll() { return repo.findAll(); }
    
    public List<Sale> findAllByWorkplace(GasStation sellLocation) { return repo.findBySellLocation(sellLocation); }
}
