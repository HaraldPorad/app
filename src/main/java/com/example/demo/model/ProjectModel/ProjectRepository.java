package com.example.demo.model.ProjectModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    List<Project> findByCustomer(String name);
    List<Project> findBySalesPersonId(Long SalesPersonId);

    @Query("""
            SELECT 
                p 
            FROM Project p 
            LEFT JOIN FETCH p.salesPerson
            """)
    List<Project> findAllWithSalesPerson();

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.salesPerson WHERE p.id = :id")
    Optional<Project> findByIdWithSalesPerson(@Param("id") Long id);
}
