package gasChain.repository;

import gasChain.entity.CreditCardAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardAccountRepository extends CardAccountRepository<CreditCardAccount, Long> {

}
