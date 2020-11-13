package pipi.api.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.profile.domain.Portfolio;
import pipi.api.domain.profile.dto.AddPortfolioRequest;
import pipi.api.domain.profile.dto.SelectPortfolioRequest;
import pipi.api.domain.profile.dto.ShowProfileResponse;
import pipi.api.domain.profile.service.ProfileService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ShowProfileResponse getProfile(@RequestParam @NotBlank String email) {
        return profileService.getProfile(email);
    }

    @GetMapping("/portfolio")
    public List<Portfolio> getPortfolios(@RequestParam @NotBlank String email) {
        return profileService.getPortfolios(email);
    }

    @PostMapping("/portfolio")
    public void addPortfolio(@RequestBody @Valid AddPortfolioRequest addPortfolioRequest) {
        profileService.addPortfolio(addPortfolioRequest);
    }

    @PutMapping("/portfolio")
    public void selectPortfolio(@RequestBody @Valid SelectPortfolioRequest selectPortfolioRequest) {
        profileService.selectPortfolio(selectPortfolioRequest);
    }
}