package gasChain.repository;

import gasChain.entity.CashPayment;
import org.springframework.stereotype.Repository;

@Repository
public interface CashPaymentRepository extends PaymentRepository<CashPayment, Long> {

}
