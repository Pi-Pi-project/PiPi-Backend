package pipi.api.domain.profile.dto;

import lombok.Builder;
import lombok.Getter;
import pipi.api.domain.profile.domain.Portfolio;
import pipi.api.domain.user.domain.UserSkillset;

import java.util.List;

@Getter
@Builder
public class ShowProfileResponse {
    private final String userEmail;
    private final String profileImg;
    private final String nickname;
    private final List<UserSkillset> skills;
    private final String giturl;
    private final String introduce;
    private final Portfolio firstPortfolio;
    private final Portfolio secondPortfolio;
}
