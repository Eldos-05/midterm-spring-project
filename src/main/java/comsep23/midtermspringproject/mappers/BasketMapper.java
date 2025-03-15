package comsep23.midtermspringproject.mappers;

import comsep23.midtermspringproject.DTO.BasketDTO;
import comsep23.midtermspringproject.entity.Basket;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface BasketMapper {

    Basket toBasket(BasketDTO basketDTO);
    BasketDTO toBasketDTO(Basket basket);
    List<BasketDTO>toBasketDTOList(List<Basket> baskets);

}
