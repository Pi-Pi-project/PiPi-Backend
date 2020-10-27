package pipi.api.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pipi.api.domain.profile.dto.ShowProfileResponse;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.UserSkillset;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.domain.repository.UserSkillsetRepository;
import pipi.api.global.error.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final UserSkillsetRepository userSkillsetRepository;

    @Override
    public ShowProfileResponse getProfile(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        List<UserSkillset> skills = userSkillsetRepository.findAllByUserEmail(userEmail);
        return ShowProfileResponse.builder()
                .profileImg(user.getProfileImage())
                .nickname(user.getNickname())
                .skills(skills)
                .giturl(user.getGiturl())
                .introduce(user.getIntroduce())
                .build();
    }
}
