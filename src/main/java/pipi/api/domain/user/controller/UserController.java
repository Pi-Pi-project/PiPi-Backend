package pipi.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pipi.api.domain.user.dto.EmailCheckRequest;
import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.dto.TokenResponse;
import pipi.api.domain.user.dto.UserRegisterRequest;
import pipi.api.domain.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/email")
    public void sendEmail(@RequestBody @Valid EmailSendRequest emailSendRequest) {
        userService.sendEmail(emailSendRequest);
    }

    @PostMapping("/email/check")
    public void checkAuthCode(@RequestBody @Valid EmailCheckRequest emailCheckRequest) {
        userService.checkAuthCode(emailCheckRequest);
    }

    @PostMapping("/register")
    public TokenResponse register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }
}
