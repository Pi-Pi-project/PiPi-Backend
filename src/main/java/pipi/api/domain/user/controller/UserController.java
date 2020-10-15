package pipi.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.service.UserService;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/email")
    public void sendEmail(@RequestBody EmailSendRequest emailSendRequest) {
        userService.sendEmail(emailSendRequest);
    }
}
