package pipi.api.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetReportUsersResponse {
    private final String userEmail;
    private final String profileImg;
    private final String userNickname;
}
