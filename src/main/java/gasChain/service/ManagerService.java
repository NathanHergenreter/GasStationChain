package gasChain.service;

import gasChain.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.GasStation;
import gasChain.entity.Manager;
import gasChain.repository.ManagerRepository;

@Service
public class ManagerService extends EmployeeService {

	@Autowired
	public ManagerService(ManagerRepository repo) { super(repo); }
	
	@Override
	public ManagerRepository repo() { return (ManagerRepository) repo; }
	
	public Manager findByStore(GasStation store) { return repo().findByStore(store); }

	public void deleteEmployee(Employee e) { repo.delete(e);}
}
