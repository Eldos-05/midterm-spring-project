package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.DTO.BasketDTO;
import comsep23.midtermspringproject.entity.Basket;
import comsep23.midtermspringproject.mappers.BasketMapper;
import comsep23.midtermspringproject.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;

    @Autowired
    public BasketService(BasketRepository basketRepository, BasketMapper basketMapper) {
        this.basketRepository = basketRepository;
        this.basketMapper = basketMapper;
    }

    public List<Basket> findByUserId(Long userId) {
        List<Basket> baskets;
        baskets = basketRepository.findAllByUserId(userId);
        return baskets;
    }


    public List<BasketDTO> toBasketDTOList(List<Basket> baskets) {
        return basketMapper.toBasketDTOList(baskets);
    }

    public List<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    public Optional<Basket> getBasketById(Long id) {
        return basketRepository.findById(id);
    }

    public Basket createBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public Basket updateBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public void deleteBasket(Long id) {
        if (basketRepository.existsById(id)) {
            basketRepository.deleteById(id);
        } else {
            throw new RuntimeException("Basket with id " + id + " not found.");
        }
    }
}

