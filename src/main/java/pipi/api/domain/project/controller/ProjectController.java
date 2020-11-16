package pipi.api.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.project.dto.CreateProjectRequest;
import pipi.api.domain.project.dto.CreateTodoRequest;
import pipi.api.domain.project.dto.GetMyProjectResponse;
import pipi.api.domain.project.dto.GetTodoResponse;
import pipi.api.domain.project.service.ProjectService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public void createProject(@RequestBody @Valid CreateProjectRequest createProjectRequest) {
        projectService.createProject(createProjectRequest);
    }

    @GetMapping
    public List<GetMyProjectResponse> getMyProject(@PageableDefault(size = 10) @NotBlank Pageable pageable) {
        return projectService.getMyProject(pageable);
    }

    @PostMapping("/todo")
    public void createTodo(@RequestBody @NotBlank CreateTodoRequest createTodoRequest) {
        projectService.createTodo(createTodoRequest);
    }

    @GetMapping("/todo")
    public List<GetTodoResponse> getTodo(@RequestParam Long id, @RequestParam String date) {
        return projectService.getTodo(id, date);
    }

    @PutMapping("/todo")
    public void successTodo(@PathVariable Long id) {
        projectService.successTodo(id);
    }

    @PutMapping("/complete")
    public void finishProject(@PathVariable Long id) {
        projectService.finishProject(id);
    }
}
