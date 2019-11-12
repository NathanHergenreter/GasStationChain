package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Expenses;
import gasChain.entity.GasStation;
import gasChain.repository.ExpensesRepository;

@Service
public class ExpensesService extends GenericService<Expenses, Long, ExpensesRepository> {

    @Autowired
	public ExpensesService(ExpensesRepository r) {
		super(r);
	}
    
    public Expenses findByStore(GasStation store) {
    	return getRepository().findByStore(store);
    }

}
