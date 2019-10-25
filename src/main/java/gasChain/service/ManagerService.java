package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.repository.ManagerRepository;

@Service
public class ManagerService extends EmployeeService {

	@Autowired
	public ManagerService(ManagerRepository repo) { super(repo); }

}
