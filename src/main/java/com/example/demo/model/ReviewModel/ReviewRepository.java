package com.example.demo.model.ReviewModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReviewRepository extends JpaRepository<Review, Long>{

    List<Review> findByConsultantId(Long consultantId);
    List<Review> findByProjectId(Long projectId);

    @Query("""
            SELECT 
                r 
            FROM Review r
            LEFT JOIN FETCH r.consultant
            LEFT JOIN FETCH r.project p
            LEFT JOIN FETCH p.salesPerson
    """)
    List<Review> findAllWithDetails();

    @Query("""
            SELECT 
                r
            FROM Review r
            LEFT JOIN FETCH r.consultant c
            LEFT JOIN FETCH r.project p
            LEFT JOIN FETCH p.salesPerson sp
            WHERE (:customer IS NULL OR LOWER(p.customer) LIKE LOWER(CONCAT('%', CAST(:customer AS string), '%')))
                    AND (:consultant IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', CAST(:consultant AS string), '%')))
                """)
    List<Review> searchReviews(
        @Param("customer") String customer,
        @Param("consultant") String consultant
    );
   
}
