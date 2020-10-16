package pipi.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pipi.api.domain.user.domain.EmailVerification;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.enums.EmailVerificationStatus;
import pipi.api.domain.user.domain.repository.EmailVerificationRepository;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.dto.EmailCheckRequest;
import pipi.api.domain.user.dto.EmailSendRequest;
import pipi.api.domain.user.dto.TokenResponse;
import pipi.api.domain.user.dto.UserRegisterRequest;
import pipi.api.domain.user.exception.InvalidAuthCodeException;
import pipi.api.domain.user.exception.InvalidAuthEmailException;
import pipi.api.domain.user.exception.UserAlreadyExistException;
import pipi.api.global.config.JwtTokenProvider;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${secret.prefix}")
    private String prefix;

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

    @Override
    public void checkAuthCode(EmailCheckRequest emailCheckRequest) {
        String email = emailCheckRequest.getEmail();
        String code = emailCheckRequest.getCode();

        EmailVerification emailVerification = emailVerificationRepository.findById(email)
                .orElseThrow(InvalidAuthEmailException::new);

        if(!emailVerification.getCode().equals(code))
            throw new InvalidAuthCodeException();

        emailVerificationRepository.save(emailVerification.verify());
    }

    @Override
    public TokenResponse register(UserRegisterRequest userRegisterRequest) {
        String email = userRegisterRequest.getEmail();
        String password = passwordEncoder.encode(userRegisterRequest.getPassword());
        
        userRepository.save(
                User.builder()
                        .email(email)
                        .password(password)
                        .nickname(userRegisterRequest.getNickname())
                        .build()
        );

        return responseToken(email);
    }

    private TokenResponse responseToken(String email) {
        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(email))
                .refreshToken(jwtTokenProvider.generateRefreshToken(email))
                .tokenType(prefix)
                .build();
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
