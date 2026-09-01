package com.example.demo.model.ReviewModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long>{

    List<Review> findById(long id); // find by id
    List<Review> findByProjectId(Long ProjectId);
    List<Review> findByProjectIdIn(List<Long> projectId);
    List<Review> findByCustomerId(Long customerId);
    List<Review> findByConsultantId(Long consultantId);

    // todo find be scores maybe
    
}
