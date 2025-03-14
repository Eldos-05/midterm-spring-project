package comsep23.midtermspringproject.repository;

import comsep23.midtermspringproject.entity.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SneakerRepository extends JpaRepository<Sneaker, Long> {
    List<Sneaker> findAll();
}
