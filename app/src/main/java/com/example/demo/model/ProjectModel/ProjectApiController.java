package com.example.demo.model.ProjectModel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SalesPeopleModel.SalesPerson;


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

    public record CreateProjectRequest(String customer, SalesPerson salesPerson) {} 

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody CreateProjectRequest request) {
        if (request.customer.trim() == null || request.customer.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Project p = new Project(request.customer().trim(), request.salesPerson());
        projectService.saveProject(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProjectDto.fromEntity(p));
    }
}