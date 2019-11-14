package gasChain.service;

import gasChain.entity.RewardMembershipAccount;
import gasChain.repository.RewardMembershipAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardMembershipAccountService extends GenericService<RewardMembershipAccount, String, RewardMembershipAccountRepository> {

    @Autowired
    RewardMembershipAccountService(RewardMembershipAccountRepository rewardsMembershipAccountRepository) {
        super(rewardsMembershipAccountRepository);
    }


}
