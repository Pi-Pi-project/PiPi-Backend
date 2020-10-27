package pipi.api.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.profile.dto.ShowProfileResponse;
import pipi.api.domain.profile.service.ProfileService;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ShowProfileResponse getProfile(@RequestParam(value = "email", required = true) String userEmail) {
        System.out.println(userEmail);
        return profileService.getProfile(userEmail);
    }
}
