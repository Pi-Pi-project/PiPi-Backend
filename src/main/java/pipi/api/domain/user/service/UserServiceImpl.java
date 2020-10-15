package pipi.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pipi.api.domain.user.domain.EmailVerification;
import pipi.api.domain.user.domain.enums.EmailVerificationStatus;
import pipi.api.domain.user.domain.repository.EmailVerificationRepository;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.dto.EmailVerifyRequest;
import pipi.api.domain.user.exception.InvalidAuthCodeException;
import pipi.api.domain.user.exception.InvalidEmailException;
import pipi.api.domain.user.exception.UserAlreadyExistExcpetion;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    private final EmailService emailService;

    private void duplicateCheck(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistExcpetion();
        });
    }

    @Override
    public void sendEmail(EmailSendRequest emailSendRequest) {
        String code = randomCode();
        String email = emailSendRequest.getEmail();
        duplicateCheck(email);
        emailService.sendMail(email, code);
        emailVerificationRepository.save(
                EmailVerification.builder()
                    .email(email)
                    .code(code)
                    .status(EmailVerificationStatus.UNVERIFIED)
                    .build()
        );
    }

    @Override
    public void verifyCode(EmailVerifyRequest emailVerifyRequest) {
        String email = emailVerifyRequest.getEmail();
        String code = emailVerifyRequest.getCode();

        EmailVerification emailVerification = emailVerificationRepository.findById(email)
                .orElseThrow(InvalidEmailException::new);

        if (!emailVerification.getCode().equals(code))
            throw new InvalidAuthCodeException();

        emailVerificationRepository.save(emailVerification.verify());
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
