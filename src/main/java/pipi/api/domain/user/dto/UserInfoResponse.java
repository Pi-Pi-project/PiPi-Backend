package pipi.api.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private final String email;
    private final String profileImg;
}
