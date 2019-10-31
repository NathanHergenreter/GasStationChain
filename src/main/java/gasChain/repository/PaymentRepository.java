package gasChain.repository;

import gasChain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface PaymentRepository<T extends Payment, ID extends Serializable> extends JpaRepository<T, ID> {
}
