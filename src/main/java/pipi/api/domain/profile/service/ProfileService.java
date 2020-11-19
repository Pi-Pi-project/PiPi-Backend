package pipi.api.domain.profile.service;

import pipi.api.domain.profile.domain.Portfolio;
import pipi.api.domain.profile.dto.AddPortfolioRequest;
import pipi.api.domain.profile.dto.SelectPortfolioRequest;
import pipi.api.domain.profile.dto.ShowProfileResponse;

import java.util.List;

public interface ProfileService {
    ShowProfileResponse getProfile(String userEmail);
    void addPortfolio(AddPortfolioRequest addPortfolioRequest);
    List<Portfolio> getPortfolios(String userEmail);
    void selectPortfolio(SelectPortfolioRequest selectPortfolioRequest);
}
