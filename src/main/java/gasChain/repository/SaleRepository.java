package gasChain.repository;

import gasChain.entity.GasStation;
import gasChain.entity.Item;
import gasChain.entity.Receipt;
import gasChain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findBySellLocation(GasStation sellLocation);

    List<Sale> findAllBySellLocationAndAndItem(GasStation sellLocation, Item item);
    List<Sale> findByReceipt(Receipt receipt);
}
