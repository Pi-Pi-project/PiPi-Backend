package pipi.api.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.auth.dto.RefreshResponse;
import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.auth.service.AuthService;
import pipi.api.domain.user.dto.TokenResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public TokenResponse login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        return authService.login(userLoginRequest);
    }

    @GetMapping("/refresh")
    public RefreshResponse refresh(@RequestHeader("x-refresh-token") String token) {
        return authService.refresh(token);
    }
}
