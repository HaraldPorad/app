package com.example.demo.model.ReviewModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReviewRepository extends JpaRepository<Review, Long>{

    List<Review> findByConsultantId(Long consultantId);
    List<Review> findByProjectId(Long projectId);

    @Query("""
            SELECT r 
            FROM Review r
            LEFT JOIN FETCH r.consultant
            LEFT JOIN FETCH r.project p
            LEFT JOIN FETCH p.salesPerson
    """)
    List<Review> findAllWithDetails();
   
}
