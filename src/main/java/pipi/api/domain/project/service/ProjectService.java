package pipi.api.domain.project.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.project.dto.*;

import java.util.List;

public interface ProjectService {
    void createProject(CreateProjectRequest createProjectRequest);
    List<GetMyProjectResponse> getMyProject(Pageable pageable);
    GetProjectTitleResponse getProjectTitle(Long id);
    void createTodo(CreateTodoRequest createTodoRequest);
    void successTodo(Long id);
    List<GetTodoResponse> getTodo(Long id, String date);
    void finishProject(FinishProjectRequest finishProjectRequest);
}
