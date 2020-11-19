package pipi.api.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetReportUsersResponse {
    private final String reportedEmail;
    private final String reporterEmail;
    private final String profileImg;
    private final String userNickname;
}
