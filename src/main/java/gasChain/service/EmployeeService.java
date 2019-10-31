package gasChain.service;

import gasChain.entity.Employee;
import gasChain.repository.EmployeeRepository;


public abstract class EmployeeService<T extends Employee, R extends EmployeeRepository<T>>
        extends GenericService<T, Long, R> {

    public EmployeeService(R r) {
        super(r);
    }

    public Employee findByUsername(String username) {
        return getRepository().findByUsername(username);
    }

    public boolean existsUser(String username) {
        return getRepository().findByUsername(username) != null;
    }

    public boolean hasCorrectAuth(Employee employee, String authority) {
        return employee.isAuth(authority);
    }

    public boolean hasCorrectAuth(String username, String authority) {
        return hasCorrectAuth(findByUsername(username), authority);
    }
}
