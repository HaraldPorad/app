package com.example.demo.model.ConsultantModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "consultants")
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long managerId;

    public Consultant() {}

    public Consultant(String name, Long managerId) { 
        this.name = name;
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return String.format("Konsult[id=%d, name='%s', managerId='%s']", id, name, managerId);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getManager() { return managerId; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setManager(Long managerId) { this.managerId = managerId; }
}