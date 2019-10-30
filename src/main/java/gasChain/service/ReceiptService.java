package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Receipt;
import gasChain.repository.ReceiptRepository;

@Service
public class ReceiptService extends GenericService<Receipt, Long, ReceiptRepository> {

	@Autowired
	ReceiptService(ReceiptRepository receiptRepository) { super(receiptRepository); }
}
