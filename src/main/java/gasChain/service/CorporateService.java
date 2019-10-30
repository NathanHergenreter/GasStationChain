package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.repository.CorporateRepository;

@Service
public class CorporateService extends EmployeeService {

	@Autowired
	public CorporateService(CorporateRepository repo) { super(repo); }

	@Override
	public CorporateRepository repo() { return (CorporateRepository) repo; }
	
}
