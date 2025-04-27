package comsep23.midtermspringproject.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemDTO {
    private Long id;
    private Long basketId;
    private Long sneakerId;
    private int quantity;
}
