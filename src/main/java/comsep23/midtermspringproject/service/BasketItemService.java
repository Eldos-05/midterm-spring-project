package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.entity.BasketItem;
import comsep23.midtermspringproject.repository.BasketItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketItemService {

    private final BasketItemRepository basketItemRepository;

    @Autowired
    public BasketItemService(BasketItemRepository basketItemRepository) {
        this.basketItemRepository = basketItemRepository;
    }

    public List<BasketItem> findBasketItemsByBasketId(Long basketId) {
        return basketItemRepository.findByBasketId(basketId);
    }

    public List<BasketItem> getAllBasketItems() {
        return basketItemRepository.findAll();
    }

    public Optional<BasketItem> getBasketItemById(int id) {
        return basketItemRepository.findById(id);
    }

    public BasketItem createBasketItem(BasketItem basketItem) {
        return basketItemRepository.save(basketItem);
    }

    public BasketItem updateBasketItem(BasketItem basketItem) {
        return basketItemRepository.save(basketItem);
    }

    public void deleteBasketItem(int id) {
        if (basketItemRepository.existsById(id)) {
            basketItemRepository.deleteById(id);
        } else {
            throw new RuntimeException("BasketItem with id " + id + " not found.");
        }
    }
}