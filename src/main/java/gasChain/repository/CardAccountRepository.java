package gasChain.repository;

import gasChain.entity.CardAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface CardAccountRepository<T extends CardAccount, ID extends Serializable> extends JpaRepository<T, ID> {

}
