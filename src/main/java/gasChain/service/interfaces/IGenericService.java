package gasChain.service.interfaces;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericService<T, ID extends Serializable> {

    long count();

    boolean isEmpty();

    void deleteById(ID id);

    Collection<T> findAll();

    T findById(ID id);

    void save(T entity);

}