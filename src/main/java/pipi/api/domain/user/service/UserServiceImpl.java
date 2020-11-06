package pipi.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pipi.api.domain.user.domain.EmailVerification;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.UserSkillset;
import pipi.api.domain.user.domain.enums.Admin;
import pipi.api.domain.user.domain.enums.EmailVerificationStatus;
import pipi.api.domain.user.domain.repository.EmailVerificationRepository;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.domain.repository.UserSkillsetRepository;
import pipi.api.domain.user.dto.*;
import pipi.api.domain.user.exception.InvalidAuthCodeException;
import pipi.api.domain.user.exception.InvalidAuthEmailException;
import pipi.api.domain.user.exception.UserAlreadyExistException;
import pipi.api.global.S3Service;
import pipi.api.global.config.AuthenticationFacade;
import pipi.api.global.config.JwtTokenProvider;
import pipi.api.global.error.exception.UserNotFoundException;

import java.io.File;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserSkillsetRepository userSkillsetRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationFacade authenticationFacade;
    private final S3Service s3Service;

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
    public void passwordChangeSendEmail(EmailSendRequest emailSendRequest) {
        userRepository.findByEmail(emailSendRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);
        String code = randomCode();
        emailService.sendEmail(emailSendRequest.getEmail(), code);
        emailVerificationRepository.save(
                EmailVerification.builder()
                        .email(emailSendRequest.getEmail())
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

        if (!emailVerification.getCode().equals(code))
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
                        .profileImage("user.jpg")
                        .admin(Admin.USER)
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

    @SneakyThrows
    @Override
    public void setProfile(SetProfileRequest setProfileRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        String imageName;
        if (setProfileRequest.getProfileImg() != null) {
            imageName = UUID.randomUUID().toString();
            s3Service.upload(setProfileRequest.getProfileImg(), UUID.randomUUID().toString());
            userRepository.save(user.setProfile(imageName, setProfileRequest.getGiturl(), setProfileRequest.getIntroduce()));
        }
        if (setProfileRequest.getSkills() != null) {
            for (String skill : setProfileRequest.getSkills()) {
                userSkillsetRepository.save(
                        UserSkillset.builder()
                                .userEmail(authenticationFacade.getUserEmail())
                                .skill(skill)
                                .build()
                );
            }
        }
    }

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        userRepository.save(user.changePassword(passwordEncoder.encode(passwordChangeRequest.getPassword())));
    }

    private String randomCode() {
                String[] codes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

        Random random = new Random(System.currentTimeMillis());
        int tableLength = codes.length;
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            buf.append(codes[random.nextInt(tableLength)]);
        }

        return buf.toString();
    }
}
