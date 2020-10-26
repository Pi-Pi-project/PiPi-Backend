package pipi.api.domain.user.service;

import pipi.api.domain.user.dto.*;

public interface UserService {
    void sendEmail(EmailSendRequest emailSendRequest);
    void passwordChangeSendEmail(EmailSendRequest emailSendRequest);
    void checkAuthCode(EmailCheckRequest emailCheckRequest);
    TokenResponse register(UserRegisterRequest userRegisterRequest);
    void setProfile(SetProfileRequest setProfileRequest);
    void changePassword(PasswordChangeRequest passwordChangeRequest);
}
