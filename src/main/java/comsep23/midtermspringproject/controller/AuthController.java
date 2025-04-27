package comsep23.midtermspringproject.controller;

import comsep23.midtermspringproject.DTO.RegistrationUserDto;
import comsep23.midtermspringproject.config.JwtRequest;
import comsep23.midtermspringproject.config.JwtResponse;
import comsep23.midtermspringproject.config.TokenRefreshRequest;
import comsep23.midtermspringproject.entity.RefreshToken;
import comsep23.midtermspringproject.service.AuthService;
import comsep23.midtermspringproject.service.RefreshTokenService;
import comsep23.midtermspringproject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // Генерируем новый accessToken и refreshToken
                    String token = jwtTokenUtils.generateToken(user);
                    String refreshToken = jwtTokenUtils.generateRefreshToken(user);
                    return ResponseEntity.ok(new JwtResponse(token, refreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh not found in database."));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
