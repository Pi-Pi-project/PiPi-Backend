package pipi.api.domain.project.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProjectTitleResponse {
    private String title;
}
