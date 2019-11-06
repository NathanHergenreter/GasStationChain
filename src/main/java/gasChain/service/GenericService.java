package gasChain.service;

import gasChain.service.interfaces.IGenericService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class GenericService<T, ID extends Serializable, R extends JpaRepository<T, ID>>
		implements IGenericService<T, ID> {
	private R r;

	public GenericService(R r) {
		this.r = r;
	}

	@Override
	@Transactional
	public long count() {
		return r.count();
	}

	@Override
	@Transactional
	public boolean isEmpty() {
		return r.count() <= 0;
	}
	
	@Override
	@Transactional
	public void delete(T t) {
		r.delete(t);
	}

	@Override
	@Transactional
	public void deleteById(ID id) {
		r.deleteById(id);
	}

	@Override
	@Transactional
	public List<T> findAll() {
		return r.findAll();
	}

	@Override
	@Transactional
	@NotNull
	public T findById(ID id) {
		Optional<T> t = r.findById(id);
		return nullCheck(t, "Invalid request: no such id: " + id);
	}

	public R getRepository() {
		return r;
	}

	/**
	 * checks if an Optional object contains an object or not
	 *
	 * @param t            type of object
	 * @param errorMessage error message to throw if not found
	 * @return retrieved object
	 */
	@NotNull
	public T nullCheck(Optional<T> t, String errorMessage) {
		if (!t.isPresent()) {
			throw new ServiceException(errorMessage);
		} else {
			return t.get();
		}
	}

    @Override
    @Transactional
    public T save(T entity) {
        return r.save(entity);
    }
}
