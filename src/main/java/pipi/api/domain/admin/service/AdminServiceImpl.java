package pipi.api.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pipi.api.domain.admin.exception.NotAdminException;
import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.enums.Admin;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.dto.TokenResponse;
import pipi.api.global.config.security.JwtTokenProvider;
import pipi.api.global.error.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${secret.prefix}")
    private String prefix;

    @Override
    public TokenResponse adminLogin(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .filter(u -> passwordEncoder.matches(userLoginRequest.getPassword(), u.getPassword()))
                .orElseThrow(UserNotFoundException::new);
        if (user.getAdmin() != Admin.ADMIN)
            throw new NotAdminException();
        return responseToken(userLoginRequest.getEmail());
    }

    private TokenResponse responseToken(String email) {
        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(email))
                .refreshToken(jwtTokenProvider.generateRefreshToken(email))
                .tokenType(prefix)
                .build();
    }
}
