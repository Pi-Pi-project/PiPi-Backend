package pipi.api.domain.user.service;

import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.dto.EmailVerifyRequest;

public interface UserService {
    void sendEmail(EmailSendRequest emailSendRequest);
    void verifyCode(EmailVerifyRequest emailVerifyRequest);
}
