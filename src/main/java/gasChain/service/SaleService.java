package gasChain.service;

import gasChain.entity.CardAccount;
import gasChain.entity.GasStation;
import gasChain.entity.Receipt;
import gasChain.entity.Sale;
import gasChain.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService extends GenericService<Sale, Long, SaleRepository> {

    public SaleService(SaleRepository saleRepository) {
        super(saleRepository);
    }

    List<Sale> findBySellLocation(GasStation sellLocation) {
        return getRepository().findBySellLocation(sellLocation);
    }

    List<Sale> findByCardAccount(CardAccount cardAccount) {
        return getRepository().findByCardAccount(cardAccount);
    }

    List<Sale> findByReceipt(Receipt receipt) {
        return getRepository().findByReceipt(receipt);
    }
}
