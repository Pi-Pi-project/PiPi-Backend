package pipi.api.domain.project.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CreateTodoRequest {
    @NotBlank
    private String todo;

    @NotBlank
    private String date;

    @NotNull
    private Long projectId;
}
