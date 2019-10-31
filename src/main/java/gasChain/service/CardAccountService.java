package gasChain.service;

import gasChain.entity.CardAccount;
import gasChain.repository.CardAccountRepository;

import java.io.Serializable;


public abstract class CardAccountService<T extends CardAccount, ID extends Serializable, R extends CardAccountRepository<T, ID>>
        extends GenericService<T, ID, R> {

    public CardAccountService(R r) {
        super(r);
    }
}
