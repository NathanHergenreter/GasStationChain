package gasChain.service;

import gasChain.entity.CashPayment;
import gasChain.repository.CashPaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class CashPaymentService extends PaymentService<CashPayment, Long, CashPaymentRepository> {
    public CashPaymentService(CashPaymentRepository cashPaymentRepository) {
        super(cashPaymentRepository);
    }
}
