package com.example.demo.model.ReviewModel;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> FilterByReviewId(long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    public List<Review> FilterByProjectId(Long projectId) {
        return reviewRepository.findByProjectId(projectId);
    }

    public List<Review> FilterByCustomerId(Long customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }
    
    public List<Review> FilterByConsultantId(Long consultantId) {
        return reviewRepository.findByConsultantId(consultantId);
    }

}

