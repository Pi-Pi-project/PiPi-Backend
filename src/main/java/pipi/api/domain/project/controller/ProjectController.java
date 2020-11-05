package pipi.api.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.project.dto.CreateProjectRequest;
import pipi.api.domain.project.service.ProjectService;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public void createProject(@RequestBody CreateProjectRequest createProjectRequest) {
        projectService.createProject(createProjectRequest);
    }
}
