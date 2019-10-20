package gasChain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.entity.Employee;
import gasChain.entity.GasStation;
import gasChain.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;
    
    public EmployeeService add(Employee employee) { repo.save(employee); return this; }
    
    public boolean isEmpty() { return repo.count() <= 0; }
    
    public List<Employee> findAll() { return repo.findAll(); }
    
    public List<Employee> findAllByWorkplace(GasStation workplace) { return repo.findByWorkplace(workplace); }
}
