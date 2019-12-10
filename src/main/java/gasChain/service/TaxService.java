package gasChain.service;

import gasChain.entity.*;
import gasChain.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TaxService extends GenericService<Tax, String, TaxRepository> {

    @Autowired
    TaxService(TaxRepository taxRepository) {
        super(taxRepository);
    }

    public Tax findByType(String type) { return getRepository().findByType(type); }

    @Transactional
    public Tax addItem(String type, Item item) {
        Tax tax = findByType(type);
        item.setTax(tax);
        tax.addItem(item);

        return tax;
    }

    @Transactional
    public Tax addEmployee(String type, Employee employee) {
        Tax tax = findByType(type);
        employee.setTax(tax);
        tax.addEmployee(employee);

        return tax;
    }
}
