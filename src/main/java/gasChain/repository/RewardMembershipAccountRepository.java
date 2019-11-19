package gasChain.repository;

import gasChain.entity.Employee;
import gasChain.entity.RewardMembershipAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface RewardMembershipAccountRepository extends JpaRepository<RewardMembershipAccount, String> {
    Set<RewardMembershipAccount> findAllByCreateByAndCreatedOnAfter(Employee employee, Date date);
}
