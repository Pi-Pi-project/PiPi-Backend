package pipi.api.domain.project.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.project.dto.CreateProjectRequest;
import pipi.api.domain.project.dto.GetMyProjectResponse;

import java.util.List;

public interface ProjectService {
    void createProject(CreateProjectRequest createProjectRequest);
    List<GetMyProjectResponse> getMyProject(Pageable pageable);
}
