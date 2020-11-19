package pipi.api.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pipi.api.domain.profile.domain.Portfolio;
import pipi.api.domain.profile.domain.repository.PortfolioRepository;
import pipi.api.domain.profile.dto.AddPortfolioRequest;
import pipi.api.domain.profile.dto.SelectPortfolioRequest;
import pipi.api.domain.profile.dto.ShowProfileResponse;
import pipi.api.domain.profile.exception.PortfolioNotFoundException;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.UserSkillset;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.domain.repository.UserSkillsetRepository;
import pipi.api.global.config.security.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final UserSkillsetRepository userSkillsetRepository;
    private final PortfolioRepository portfolioRepository;
    private final AuthenticationFacade authenticationFacade;

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
                .firstPortfolio(user.getFirstPortfolio())
                .secondPortfolio(user.getSecondPortfolio())
                .userEmail(userEmail)
                .build();
    }

    @Override
    public void addPortfolio(AddPortfolioRequest addPortfolioRequest) {
        portfolioRepository.save(
                Portfolio.builder()
                        .title(addPortfolioRequest.getTitle())
                        .giturl(addPortfolioRequest.getGiturl())
                        .introduce(addPortfolioRequest.getIntroduce())
                        .userEmail(authenticationFacade.getUserEmail())
                        .build()
        );
    }

    @Override
    public List<Portfolio> getPortfolios(String userEmail) {
        return portfolioRepository.findAllByUserEmail(userEmail);
    }

    @Override
    public void selectPortfolio(SelectPortfolioRequest selectPortfolioRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        if (selectPortfolioRequest.getFirst() != null) {
            Portfolio first = portfolioRepository.findById(selectPortfolioRequest.getFirst())
                    .orElseThrow(PortfolioNotFoundException::new);
            userRepository.save(user.setFirstPortfolio(first));
        }
        if (selectPortfolioRequest.getSecond() != null) {
            Portfolio second = portfolioRepository.findById(selectPortfolioRequest.getSecond())
                    .orElseThrow(PortfolioNotFoundException::new);
            userRepository.save(user.setSecondPortfolio(second));
        }
    }
}
