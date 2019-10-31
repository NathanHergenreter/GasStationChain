package gasChain.service;

import gasChain.entity.CreditCardAccount;
import gasChain.repository.CreditCardAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditCardAccountService extends CardAccountService<CreditCardAccount, Long, CreditCardAccountRepository> {

    public CreditCardAccountService(CreditCardAccountRepository creditCardAccountRepository) {
        super(creditCardAccountRepository);
    }
}
