package comsep23.midtermspringproject.DTO;

import lombok.Data;

import java.util.List;

@Data
public class BasketDTO {
    private List<BasketItemDTO> items;
    private double totalPrice;
}
