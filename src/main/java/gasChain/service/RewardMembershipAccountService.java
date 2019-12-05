package gasChain.service;

import gasChain.entity.Employee;
import gasChain.entity.RewardMembershipAccount;
import gasChain.repository.RewardMembershipAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public RewardMembershipAccount findByPhoneNumber(String phoneNumber) {
        Optional<RewardMembershipAccount> rma = getRepository().findByPhoneNumber(phoneNumber);
        return nullCheck(rma, "No Such Phone Number Found");
    }

    public RewardMembershipAccount findByEmail(String email) {
        Optional<RewardMembershipAccount> rma = getRepository().findByEmail(email);
        return nullCheck(rma, "No Such Email Found");
    }
    public float findTotalUnredeemed() {
        List<RewardMembershipAccount> rewardMembershipAccounts = getRepository().findAll();
        float total = 0;
        for (RewardMembershipAccount r : rewardMembershipAccounts) {
            total += r.getRewardsBalance() / 100.0;
        }
        return total;
    }


}
