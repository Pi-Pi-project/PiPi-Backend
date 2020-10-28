package pipi.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.user.dto.*;
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

    @PostMapping
    public TokenResponse register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    @PostMapping("/profile")
    public void SetProfile(@ModelAttribute @Valid SetProfileRequest setProfileRequest) {
        userService.setProfile(setProfileRequest);
    }

    @PostMapping("/password/email")
    public void changePasswordSendEmail(@RequestBody @Valid EmailSendRequest emailSendRequest) {
        userService.passwordChangeSendEmail(emailSendRequest);
    }


    @PutMapping("/password")
    public void changePassword(@RequestBody @Valid PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(passwordChangeRequest);
    }
}
