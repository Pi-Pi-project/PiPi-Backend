package pipi.api.domain.user.service;

import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.dto.UserRegisterRequest;

public interface UserService {
    void sendEmail(EmailSendRequest emailSendRequest);
}
