package pipi.api.domain.profile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AddPortfolioRequest {
    private String giturl;
    private String introduce;
    private String title;
}
