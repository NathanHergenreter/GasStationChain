package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gasChain.entity.Expenses;
import gasChain.entity.GasStation;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

	Expenses findByStore(GasStation store);
}
