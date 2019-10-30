package gasChain.service;

import gasChain.entity.Receipt;
import gasChain.repository.ReceiptRepository;

public class ReceiptService extends GenericService<Receipt, Long, ReceiptRepository> {

    public ReceiptService(ReceiptRepository receiptRepository) {
        super(receiptRepository);
    }


}
