package com.example.demo.model.ProjectModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    List<Project> findById(long id);
    List<Project> findByCustomer(String name);
    List<Project> findBySalesPersonId(Long SalesPersonId);
    //List<Project> findByConsultantId(Long consultantId);
    
}
