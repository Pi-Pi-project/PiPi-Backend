package pipi.api.domain.user.service;

import pipi.api.domain.user.dto.EmailSendRequest;

public interface UserService {
    void duplicateCheck(String email);
    void sendEmail(EmailSendRequest emailSendRequest);
}
