package comsep23.midtermspringproject.mappers;

import comsep23.midtermspringproject.DTO.BasketItemDTO;
import comsep23.midtermspringproject.entity.BasketItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasketItemMapper {

    @Mapping(source = "basket.id", target = "basketId")
    @Mapping(source = "sneaker.id", target = "sneakerId")
    BasketItemDTO toBasketItemDTO(BasketItem basketItem);

    @Mapping(source = "basketId", target = "basket.id")
    @Mapping(source = "sneakerId", target = "sneaker.id")
    BasketItem toBasketItem(BasketItemDTO basketItemDTO);

    List<BasketItemDTO> toBasketItemDTOList(List<BasketItem> basketItemList);
}
