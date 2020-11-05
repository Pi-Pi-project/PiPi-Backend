package pipi.api.domain.project.service;

import pipi.api.domain.project.dto.CreateProjectRequest;

public interface ProjectService {
    void createProject(CreateProjectRequest createProjectRequest);
}
