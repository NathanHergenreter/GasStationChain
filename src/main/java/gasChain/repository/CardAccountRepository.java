package gasChain.repository;

import gasChain.entity.CardAccount;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CardAccountRepository<T extends CardAccount, ID extends Serializable>
        extends PaymentRepository<T, ID> {

}
