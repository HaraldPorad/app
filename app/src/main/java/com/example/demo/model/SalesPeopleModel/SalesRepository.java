package com.example.demo.model.SalesPeopleModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalesRepository extends JpaRepository<SalesPerson, Long>{

    List<SalesPerson> findByName(String name);

    @Query("""
    SELECT s FROM SalesPerson s 
    WHERE s.id IN (
        SELECT MIN(s2.id) FROM SalesPerson s2 GROUP BY LOWER(TRIM(s2.name))
    )
    ORDER BY s.name ASC
    """)
    List<SalesPerson> findAllUniqueByName();
    
}
