package gasChain.service;

import gasChain.entity.Payment;
import gasChain.repository.PaymentRepository;

import java.io.Serializable;

public abstract class PaymentService<T extends Payment, ID extends Serializable, R extends PaymentRepository<T, ID>>
        extends GenericService<T, ID, R> {
    public PaymentService(R r) {
        super(r);
    }
}
