package gasChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gasChain.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
