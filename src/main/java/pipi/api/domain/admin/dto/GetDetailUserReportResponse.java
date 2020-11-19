package pipi.api.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetDetailUserReportResponse {
    private final String reporterEmail;
    private final String reportedEmail;
    private final String reason;
}
