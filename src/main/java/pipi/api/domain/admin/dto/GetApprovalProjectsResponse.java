package pipi.api.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetApprovalProjectsResponse {
    private final Long projectId;
    private final String title;
    private final String coverImg;
}
