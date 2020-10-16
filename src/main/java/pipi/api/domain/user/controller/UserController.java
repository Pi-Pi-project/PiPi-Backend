package pipi.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.dto.EmailVerifyRequest;
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
    public void verifyCode(@RequestBody @Valid EmailVerifyRequest emailVerifyRequest) {
        userService.verifyCode(emailVerifyRequest);
    }

    @PostMapping("/register")
    public void register(@ModelAttribute @Valid UserRegisterRequest userRegisterRequest) {
        userService.register(userRegisterRequest);
    }
}
