package pipi.api.domain.user.service;

import pipi.api.domain.user.dto.EmailSendRequest;

public interface UserService {
    void sendEmail(EmailSendRequest emailSendRequest);
}
