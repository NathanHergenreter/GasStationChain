package gasChain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gasChain.entity.Employee;
import gasChain.repository.EmployeeRepository;

@Service
public abstract class EmployeeService {

	protected EmployeeRepository repo;
	
	public EmployeeService(EmployeeRepository repo) { this.repo = repo; }
	
	public EmployeeRepository repo() { return repo; }
	
	public EmployeeService add(Employee employee) { repo.save(employee); return this; }
	
	public boolean isEmpty() { return repo.count() <= 0; }
	
	public List<Employee> findAll() { return repo.findAll(); }
	
	public Employee findByUsername(String username) { return repo.findByUsername(username); }
	
	public boolean existsUser(String username) { return repo.findByUsername(username) != null; }

}
