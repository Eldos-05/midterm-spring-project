package comsep23.midtermspringproject.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class BasketItemDTO {
    private Long id;
    private Long basketId;
    private Long sneakerId;
    private int quantity;

}
