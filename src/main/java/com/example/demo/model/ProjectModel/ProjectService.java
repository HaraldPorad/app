package com.example.demo.model.ProjectModel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAllWithSalesPerson();
    }

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findByIdWithSalesPerson(projectId);
    }

    public List<Project> getByCustomer(String name) {
        return projectRepository.findByCustomer(name);
    }

    public List<Project> getProjectsBySalesPersonId(Long salesPersonId) {
        return projectRepository.findBySalesPersonId(salesPersonId);
    }
}
