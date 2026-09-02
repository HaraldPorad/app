package com.example.demo.model.ConsultantModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ConsultantRepository extends JpaRepository<Consultant, Long>{

    List<Consultant> findById(long id); // find by id
    List<Consultant> findByName(String name);
    List<Consultant> findByManagerId(Long managerId);
    
}
