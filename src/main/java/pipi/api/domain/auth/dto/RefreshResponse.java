package pipi.api.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshResponse {
    private String accessToken;
    private String tokenType;
}
