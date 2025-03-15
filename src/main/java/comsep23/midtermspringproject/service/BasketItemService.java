package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.DTO.BasketItemDTO;
import comsep23.midtermspringproject.entity.BasketItem;
import comsep23.midtermspringproject.mappers.BasketItemMapper;
import comsep23.midtermspringproject.repository.BasketItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketItemService  {
    private final BasketItemRepository basketItemRepository;
    private final BasketItemMapper basketItemMapper;

    @Autowired
    public BasketItemService(BasketItemRepository basketItemRepository, BasketItemMapper basketItemMapper) {
        this.basketItemRepository = basketItemRepository;
        this.basketItemMapper = basketItemMapper;
    }

    public List<BasketItemDTO> findBasketItemsById(Long basketId) {
        List<BasketItem> basketItems = basketItemRepository.findBasketItemById(basketId);
        return basketItemMapper.toBasketItemDTOList(basketItems);
    }


}
