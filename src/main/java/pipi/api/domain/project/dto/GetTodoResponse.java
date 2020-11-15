package pipi.api.domain.project.dto;

import lombok.Builder;
import lombok.Getter;
import pipi.api.domain.project.domain.enums.TodoStatus;

@Getter
@Builder
public class GetTodoResponse {
    private final String nickname;
    private final String date;
    private final String todo;
    private final TodoStatus todoStatus;
}
