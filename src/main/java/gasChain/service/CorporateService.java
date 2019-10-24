package gasChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.repository.CorporateRepository;

@Service
public class CorporateService extends UserService {

	@Autowired
	public CorporateService(CorporateRepository repo) { super(repo); }

	
	
}
