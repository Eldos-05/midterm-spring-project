package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.DTO.RegistrationUserDto;
import comsep23.midtermspringproject.DTO.UserDTO;
import comsep23.midtermspringproject.config.JwtRequest;
import comsep23.midtermspringproject.config.JwtResponse;
import comsep23.midtermspringproject.entity.User;
import comsep23.midtermspringproject.exceptions.AppError;
import comsep23.midtermspringproject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    // Метод для создания токена
    public ResponseEntity<?> createAuthToken(JwtRequest authRequest) {
        try {
            // Аутентификация
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }

        // Загружаем детали пользователя
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        // Генерация токенов
        String token = jwtTokenUtils.generateToken(userDetails);
        String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails);

        // Возвращаем оба токена
        return ResponseEntity.ok(new JwtResponse(token, refreshToken));
    }

    // Метод для регистрации нового пользователя
    public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords do not match"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "A user with the specified username already exists"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail()));
    }
}
