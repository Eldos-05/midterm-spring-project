package comsep23.midtermspringproject.repository;

import comsep23.midtermspringproject.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketItemRepository extends JpaRepository<BasketItem, Integer> {
    List<BasketItem> findBasketItemById(Long id);
}
