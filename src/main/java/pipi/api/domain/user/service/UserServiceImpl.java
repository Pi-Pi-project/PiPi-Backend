package pipi.api.domain.user.service;

import pipi.api.domain.user.domain.EmailVerification;
import pipi.api.domain.user.domain.enums.EmailVerificationStatus;
import pipi.api.domain.user.domain.repository.EmailVerificationRepository;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.dto.UserRegisterRequest;
import pipi.api.domain.user.exception.UserAlreadyExistException;

import java.util.Random;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    private final EmailService emailService;

    private void isExists(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistException();
        });
    }

    @Override
    public void sendEmail(EmailSendRequest emailSendRequest) {
        String email = emailSendRequest.getEmail();
        isExists(email);
        String code = randomCode();
        emailService.sendEmail(email, code);
        emailVerificationRepository.save(
                EmailVerification.builder()
                        .email(email)
                        .code(code)
                        .status(EmailVerificationStatus.UNVERIFIED)
                        .build()
        );
    }

    private String randomCode() {
        String[] codes = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

        Random random = new Random(System.currentTimeMillis());
        int tableLength = codes.length;
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            buf.append(codes[random.nextInt(tableLength)]);
        }

        return buf.toString();
    }
}
