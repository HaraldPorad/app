package com.example.demo.model.ProjectModel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    public List<Project> FilterByProjectId(long projectId) {
        return projectRepository.findById(projectId);
    }

    public List<Project> FilterByCustomer(String name) {
        return projectRepository.findByCustomer(name);
    }

    public List<Project> FilterBySalesPersonId(Long salesPersonId) {
        return projectRepository.findBySalesPersonId(salesPersonId);
    }

    //public List<Project> FilterByConsultantId(Long consultantId) {
    //    return projectRepository.findByConsultantId(consultantId);
    //}

}
