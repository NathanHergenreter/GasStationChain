package gasChain.service;

import gasChain.entity.Sale;
import gasChain.repository.SaleRepository;
import org.springframework.stereotype.Service;

@Service
public class SaleService extends GenericService<Sale, Long, SaleRepository> {

    public SaleService(SaleRepository saleRepository) {
        super(saleRepository);
    }
}
