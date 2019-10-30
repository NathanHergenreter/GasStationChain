package gasChain.service;

import gasChain.entity.Corporate;
import gasChain.repository.CorporateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorporateService extends EmployeeService<Corporate, CorporateRepository> {

	@Autowired
	public CorporateService(CorporateRepository corporateRepository) {
		super(corporateRepository);
	}


}
