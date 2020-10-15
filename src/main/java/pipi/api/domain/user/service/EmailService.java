package pipi.api.domain.user.service;

public interface EmailService {
    void sendMail(String email, String code);
}
