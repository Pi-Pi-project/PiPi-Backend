package pipi.api.domain.project.dto;

import lombok.Getter;

@Getter
public class CreateTodoRequest {
    private String todo;
    private Long projectId;
}
