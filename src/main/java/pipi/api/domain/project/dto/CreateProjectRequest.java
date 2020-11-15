package pipi.api.domain.project.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateProjectRequest {
    @NotNull
    private Long postId;
}
