package com.example.demo.model.ConsultantModel;

import com.example.demo.model.SalesPeopleModel.SalesPerson;
import com.example.demo.model.ProjectModel.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "consultants")
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Consultant points to their manager
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_person_id")
    private SalesPerson manager;

    // Consultant points to their project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    public Consultant() {}

    public Consultant(String name, SalesPerson manager, Project project) { 
        this.name = name;
        this.manager = manager;
        this.project = project;
    }

    @Override
    public String toString() {
        return String.format("Konsult[id=%d, name='%s', manager='%s', project='%s']", id, name, manager, project);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public SalesPerson getManager() { return manager; }
    public Project getProject() { return project; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setManager(SalesPerson manager) { this.manager = manager; }
    public void setProject(Project project) { this.project = project; }
}