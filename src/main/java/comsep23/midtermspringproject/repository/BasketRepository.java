package comsep23.midtermspringproject.repository;

import comsep23.midtermspringproject.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findBasketByBasketId(Long basketId);
}
