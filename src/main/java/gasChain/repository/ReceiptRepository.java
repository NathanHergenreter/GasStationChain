package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

}
