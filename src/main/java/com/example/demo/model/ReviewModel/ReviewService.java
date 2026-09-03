package com.example.demo.model.ReviewModel;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAllWithDetails();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByConsultantId(Long consultantId) {
        return reviewRepository.findByConsultantId(consultantId);
    }

    public List<Review> getReviewsByProject(Long projectId) {
        return reviewRepository.findByProjectId(projectId);
    }

    public List<Review> searchReviews(String customer, String consultant) {
        String cleanCustomer = (customer != null && !customer.isBlank()) ? customer.trim() : null;
        String cleanConsultant = (consultant != null && !consultant.isBlank()) ? consultant.trim() : null;

        return reviewRepository.searchReviews(cleanCustomer, cleanConsultant);
    }
}

