package gasChain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Cashier;
import gasChain.entity.GasStation;
import gasChain.repository.CashierRepository;

@Service
public class CashierService extends EmployeeService {

	@Autowired
    public CashierService(CashierRepository repo) { super(repo); }

    public List<Cashier> findAllByWorkplace(GasStation workplace) { return ((CashierRepository) repo).findByWorkplace(workplace); }
}
