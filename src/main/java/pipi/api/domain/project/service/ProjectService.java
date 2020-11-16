package pipi.api.domain.project.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.project.dto.CreateProjectRequest;
import pipi.api.domain.project.dto.CreateTodoRequest;
import pipi.api.domain.project.dto.GetMyProjectResponse;
import pipi.api.domain.project.dto.GetTodoResponse;

import java.util.List;

public interface ProjectService {
    void createProject(CreateProjectRequest createProjectRequest);
    List<GetMyProjectResponse> getMyProject(Pageable pageable);
    void createTodo(CreateTodoRequest createTodoRequest);
    void successTodo(Long id);
    List<GetTodoResponse> getTodo(Long id, String date);
    void finishProject(Long id);
}
