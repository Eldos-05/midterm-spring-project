package comsep23.midtermspringproject.repository;

import comsep23.midtermspringproject.entity.Sneaker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SneakerRepository extends CrudRepository<Sneaker, Long> {
}
