package comsep23.midtermspringproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO{

    private Long id;
    private String username;
    private String email;
}
