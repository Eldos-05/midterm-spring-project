package comsep23.midtermspringproject.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BasketDTO {
    private List<BasketItemDTO> items;
    private double totalPrice;
}
