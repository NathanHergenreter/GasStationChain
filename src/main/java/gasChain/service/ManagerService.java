package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.GasStation;
import gasChain.entity.Manager;
import gasChain.repository.ManagerRepository;

@Service
public class ManagerService extends EmployeeService<Manager, ManagerRepository> {

	@Autowired
	public ManagerService(ManagerRepository managerRepository) {
		super(managerRepository);
	}

	public Manager findByStore(GasStation store) {
		return getRepository().findByStore(store);
	}
}
