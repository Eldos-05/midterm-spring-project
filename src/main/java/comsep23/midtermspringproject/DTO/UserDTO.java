package comsep23.midtermspringproject.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO{

    private Long id;
    private String username;
    private String email;

}
