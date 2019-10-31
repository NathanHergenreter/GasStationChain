package gasChain.service;

import gasChain.entity.DebitAccount;
import gasChain.repository.DebitAccountRepository;

public class DebitAccountService extends CardAccountService<DebitAccount, Long, DebitAccountRepository> {
    public DebitAccountService(DebitAccountRepository debitAccountRepository) {
        super(debitAccountRepository);
    }

    int getAccountBalanceByCardNumber(String cardNumber) {
        return getRepository().getAccountBalanceByCardNumber(cardNumber);
    }
}
