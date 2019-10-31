package gasChain.service;

import gasChain.entity.Cashier;
import gasChain.entity.GasStation;
import gasChain.repository.CashierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashierService extends EmployeeService<Cashier, CashierRepository> {

	@Autowired
	public CashierService(CashierRepository cashierRepository) {
		super(cashierRepository);
	}

	public List<Cashier> findAllByWorkplace(GasStation workplace) {
		return getRepository().findByWorkplace(workplace);
	}
}
