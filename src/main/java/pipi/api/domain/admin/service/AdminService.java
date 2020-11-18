package pipi.api.domain.admin.service;

import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.user.dto.TokenResponse;

public interface AdminService {
    TokenResponse adminLogin(UserLoginRequest userLoginRequest);
}
