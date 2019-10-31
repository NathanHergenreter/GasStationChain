package gasChain.service;

import gasChain.entity.CreditCardAccount;
import gasChain.repository.CreditCardAccountRepository;

public class CreditCardAccountService extends CardAccountService<CreditCardAccount, Long, CreditCardAccountRepository> {

    public CreditCardAccountService(CreditCardAccountRepository creditCardAccountRepository) {
        super(creditCardAccountRepository);
    }
}
