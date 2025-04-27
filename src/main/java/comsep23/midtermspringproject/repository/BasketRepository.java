package comsep23.midtermspringproject.repository;

import comsep23.midtermspringproject.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findAllByUserId(Long userId);

    Optional<Basket> findByUserId(Long userId);
}
