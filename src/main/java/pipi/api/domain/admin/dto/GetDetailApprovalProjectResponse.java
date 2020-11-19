package pipi.api.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetDetailApprovalProjectResponse {
    private final Long projectId;
    private final String title;
    private final String giturl;
    private final String introduce;
}
