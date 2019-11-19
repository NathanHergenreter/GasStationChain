package gasChain.service;

import gasChain.entity.Receipt;
import gasChain.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService extends GenericService<Receipt, Long, ReceiptRepository> {

    @Autowired
    ReceiptService(ReceiptRepository receiptRepository) {
        super(receiptRepository);
    }

    public List<Receipt> findAllByRewardMembershipAccountIsNull() {
        return getRepository().findAllByRewardMembershipAccountIsNull();
    }

    public List<Receipt> findAllByRewardMembershipAccountIsNotNull() {
        return getRepository().findAllByRewardMembershipAccountIsNotNull();
    }
}
