package gasChain.repository;

import gasChain.entity.DebitAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitAccountRepository extends CardAccountRepository<DebitAccount, Long> {

    int getAccountBalanceByCardNumber(String cardNumber);
}
