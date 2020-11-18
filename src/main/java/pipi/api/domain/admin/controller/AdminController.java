package pipi.api.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pipi.api.domain.admin.service.AdminService;
import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.user.dto.TokenResponse;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/auth")
    public TokenResponse adminLogin(UserLoginRequest userLoginRequest) {
        return adminService.adminLogin(userLoginRequest);
    }
}
