package comsep23.midtermspringproject.DTO;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UserDTO{

    private Long id;
    private String username;
    private String email;

}
