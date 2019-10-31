package gasChain.repository;

import gasChain.entity.CardAccount;
import gasChain.entity.GasStation;
import gasChain.entity.Receipt;
import gasChain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findBySellLocation(GasStation sellLocation);

    List<Sale> findByCardAccount(CardAccount cardAccount);

    List<Sale> findByReceipt(Receipt receipt);
}
