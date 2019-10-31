package gasChain.repository;

import gasChain.entity.DebitAccount;

public interface DebitAccountRepository extends CardAccountRepository<DebitAccount, Long> {

    int getAccountBalanceByCardNumber(String cardNumber);


}
