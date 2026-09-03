package com.example.demo.model.ReviewModel;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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
    

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody CreateReviewRequest request) {
        Review created = reviewService.createReviewFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewDto.fromEntity(created));
    }

}