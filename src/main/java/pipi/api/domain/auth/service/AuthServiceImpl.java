package pipi.api.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.auth.exception.InvalidTokenException;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.dto.TokenResponse;
import pipi.api.global.config.JwtTokenProvider;
import pipi.api.global.error.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${secret.prefix}")
    private String prefix;

    @Override
    public TokenResponse login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .filter(u -> passwordEncoder.matches(userLoginRequest.getPassword(), u.getPassword()))
                .orElseThrow(UserNotFoundException::new);
        return responseToken(user.getEmail());
    }

    @Override
    public TokenResponse refresh(String token) {
        if (!jwtTokenProvider.isRefreshToken(token)) throw new InvalidTokenException();
        String email = jwtTokenProvider.getUserEmail(token);
        return responseToken(email);
    }

    private TokenResponse responseToken(String email) {
        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(email))
                .refreshToken(jwtTokenProvider.generateRefreshToken(email))
                .tokenType(prefix)
                .build();
    }
}
