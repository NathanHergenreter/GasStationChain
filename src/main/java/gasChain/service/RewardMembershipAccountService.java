package gasChain.service;

import gasChain.entity.Employee;
import gasChain.entity.RewardMembershipAccount;
import gasChain.repository.RewardMembershipAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class RewardMembershipAccountService extends GenericService<RewardMembershipAccount, String, RewardMembershipAccountRepository> {

    @Autowired
    RewardMembershipAccountService(RewardMembershipAccountRepository rewardsMembershipAccountRepository) {
        super(rewardsMembershipAccountRepository);
    }

    public Set<RewardMembershipAccount> findAllByCreateByAndCreatedOnAfter(Employee employee, Date after) {
        return getRepository().findAllByCreateByAndCreatedOnAfter(employee, after);
    }


}
