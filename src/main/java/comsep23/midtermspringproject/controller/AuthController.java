package comsep23.midtermspringproject.controller;

import comsep23.midtermspringproject.DTO.RegistrationUserDto;
import comsep23.midtermspringproject.config.JwtRequest;
import comsep23.midtermspringproject.config.JwtResponse;
import comsep23.midtermspringproject.config.TokenRefreshRequest;
import comsep23.midtermspringproject.entity.RefreshToken;
import comsep23.midtermspringproject.service.AuthService;
import comsep23.midtermspringproject.service.RefreshTokenService;
import comsep23.midtermspringproject.service.UserService;
import comsep23.midtermspringproject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login() {
        return "login-page";
    }
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtTokenUtils.generateToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    UserDetails userDetails = (UserDetails) userService.loadUserByUsername(user.getUsername());
                    String token = jwtTokenUtils.generateToken(userDetails);
                    String newRefreshToken = jwtTokenUtils.generateRefreshToken(user);
                    return ResponseEntity.ok(new JwtResponse(token, newRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found in database or expired."));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        refreshTokenService.deleteByToken(refreshToken);
        return ResponseEntity.ok("Logged out successfully");
    }
}