package pipi.api.domain.user.service;

import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.dto.EmailVerifyRequest;
import pipi.api.domain.user.dto.UserRegisterRequest;

public interface UserService {
    void sendEmail(EmailSendRequest emailSendRequest);
    void verifyCode(EmailVerifyRequest emailVerifyRequest);
    void register(UserRegisterRequest userRegisterRequest);
}
