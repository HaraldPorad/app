package com.example.demo.model.ConsultantModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsultantRepository extends JpaRepository<Consultant, Long>{

    List<Consultant> findByName(String name);
    List<Consultant> findByManagerId(Long managerId);
    List<Consultant> findByProjectId(Long projectId);

    @Query("""
        SELECT 
            c 
        FROM Consultant c 
        LEFT JOIN FETCH c.project 
        LEFT JOIN FETCH c.manager
        """)
    List<Consultant> findAllWithDetails();
}
