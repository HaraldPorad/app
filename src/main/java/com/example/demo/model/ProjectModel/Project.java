package com.example.demo.model.ProjectModel;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer;
    private Long salesPersonId;
    private List<Long> consultantIds;

    public Project() {}

    public Project(String customer, Long salesPersonId, List<Long> consultantIds) { 
        this.customer = customer;
        this.salesPersonId = salesPersonId;
        this.consultantIds = consultantIds;

    }

    @Override
    public String toString() {
        return String.format("Project[id=%d, customer='%s', salesPerson='%s', consultant='%s']",
                     id, customer, salesPersonId, consultantIds);
    }

    public Long getId() { return id; }
    public String getCustomer() { return customer; }
    public Long getSalesPersonId() { return salesPersonId; }
    public List<Long> getConsultantIds() { return consultantIds; }

    public void setId(Long id) { this.id = id; }
    public void setCustomer(String customer) { this.customer = customer; }
    public void setSalesPerson(Long salesPersonId) { this.salesPersonId = salesPersonId; }
    public void setConsultants(List<Long> consultantIds) { this.consultantIds = consultantIds; }
    // public void addConsultant(Long consultantId) { this.consultantIds.add(consultantId); }
}