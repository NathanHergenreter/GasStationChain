package gasChain.repository;

import gasChain.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllByRewardMembershipAccountIsNull();

    List<Receipt> findAllByRewardMembershipAccountIsNotNull();

}
