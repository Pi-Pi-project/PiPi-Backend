package pipi.api.domain.project.dto;

import lombok.Getter;

@Getter
public class FinishProjectRequest {
    private Long id;
    private String giturl;
    private String introduce;
}
