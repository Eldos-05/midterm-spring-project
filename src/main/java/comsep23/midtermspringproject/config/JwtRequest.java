package comsep23.midtermspringproject.config;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}