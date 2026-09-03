package com.example.demo.model.ProjectModel;

import com.example.demo.model.SalesPeopleModel.SalesPerson;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer;

    // Projects point to their sales contact
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_person_id")
    private SalesPerson salesPerson;


    public Project() {}

    public Project(String customer, SalesPerson salesPerson) { 
        this.customer = customer;
        this.salesPerson = salesPerson;

    }

    @Override
    public String toString() {
        return String.format("Project[id=%d, customer='%s', salesPerson='%s']",
                     id, customer, salesPerson);
    }

    public Long getId() { return id; }
    public String getCustomer() { return customer; }
    public SalesPerson getSalesPerson() { return salesPerson; }

    public void setId(Long id) { this.id = id; }
    public void setCustomer(String customer) { this.customer = customer; }
    public void setSalesPerson(SalesPerson salesPerson) { this.salesPerson = salesPerson; }
}