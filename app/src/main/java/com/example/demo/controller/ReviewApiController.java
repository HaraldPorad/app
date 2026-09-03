package com.example.demo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ReviewModel.Review;
import com.example.demo.model.ReviewModel.ReviewDto;
import com.example.demo.model.ReviewModel.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/reviews")
public class ReviewApiController {
    private final ReviewService reviewService;

    public ReviewApiController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/search")
    public List<ReviewDto> searchReviews(
        @RequestParam(required = false) String customer,
        @RequestParam(required = false) String consultant) {

        return reviewService.searchReviews(customer, consultant)
            .stream()
            .map(ReviewDto::fromEntity)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ReviewDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    // implementera detta när jag fattar
    //@PostMapping()
    //@ResponseStatus(HttpStatus.CREATED)
    //public ReviewDto createReview(@RequestBody CreateReviewRequest request) {
    //    Review created = reviewService.createReviewFromRequest(request);
    //    return ReviewDto.fromEntity(created);
    //}

}