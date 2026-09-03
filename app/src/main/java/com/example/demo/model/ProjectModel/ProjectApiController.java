package com.example.demo.model.ProjectModel;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ProjectModel.ProjectDto;
import com.example.demo.model.ProjectModel.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectApiController {

    private final ProjectService projectService;

    public ProjectApiController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectDto> getProjects(@RequestParam(required = false) Long salesPersonId) {
        if (salesPersonId != null) {
            return projectService.getProjectsBySalesPersonId(salesPersonId).stream()
                    .map(ProjectDto::fromEntity)
                    .toList();
        }
        return projectService.getAllProjects().stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ProjectDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}