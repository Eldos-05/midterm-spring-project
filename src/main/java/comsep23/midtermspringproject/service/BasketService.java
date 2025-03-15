package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.DTO.BasketDTO;
import comsep23.midtermspringproject.entity.Basket;
import comsep23.midtermspringproject.mappers.BasketMapper;
import comsep23.midtermspringproject.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    public List<BasketDTO>toBasketDTOList(List<Basket> baskets) {
        return basketMapper.toBasketDTOList(baskets);
    }
}
