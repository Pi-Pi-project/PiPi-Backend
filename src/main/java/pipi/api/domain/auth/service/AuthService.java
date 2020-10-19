package pipi.api.domain.auth.service;

import pipi.api.domain.auth.dto.RefreshResponse;
import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.user.dto.TokenResponse;

public interface AuthService {
    TokenResponse login(UserLoginRequest userLoginRequest);
    RefreshResponse refresh(String token);
}
