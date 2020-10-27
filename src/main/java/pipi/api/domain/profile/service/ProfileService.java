package pipi.api.domain.profile.service;

import pipi.api.domain.profile.dto.ShowProfileResponse;

public interface ProfileService {
    ShowProfileResponse getProfile(String userEmail);
}
