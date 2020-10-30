package pipi.api.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.profile.domain.Portfolio;
import pipi.api.domain.profile.dto.AddPortfolioRequest;
import pipi.api.domain.profile.dto.ShowProfileResponse;
import pipi.api.domain.profile.service.ProfileService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ShowProfileResponse getProfile(@RequestParam(value = "email", required = true) String userEmail) {
        return profileService.getProfile(userEmail);
    }

    @GetMapping("/portfolio")
    public List<Portfolio> getPortfolios() {
        return profileService.getPortfolios();
    }

    @PostMapping("/portforlio")
    public void addPortfolio(@RequestBody @Valid AddPortfolioRequest addPortfolioRequest) {
        profileService.addPortfolio(addPortfolioRequest);
    }
}