package com.example.demo.model.ReviewModel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.ConsultantModel.Consultant;
import com.example.demo.model.ConsultantModel.ConsultantRepository;
import com.example.demo.model.ProjectModel.Project;
import com.example.demo.model.ProjectModel.ProjectRepository;
import com.example.demo.model.SalesPeopleModel.SalesPerson;
import com.example.demo.model.SalesPeopleModel.SalesRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final ConsultantRepository consultantRepository;
    private final SalesRepository salesRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         ProjectRepository projectRepository,
                         ConsultantRepository consultantRepository,
                         SalesRepository salesRepository) {
        this.reviewRepository = reviewRepository;
        this.projectRepository = projectRepository;
        this.consultantRepository = consultantRepository;
        this.salesRepository = salesRepository;
    }

    @Transactional
    public Review createReviewFromRequest(CreateReviewRequest req) {
        // 1. Resolve or create SalesPerson
        SalesPerson salesPerson = null;
        if (req.salesPerson() != null && !req.salesPerson().isBlank()) {
            String salesName = req.salesPerson().trim();
            salesPerson = salesRepository.findByName(salesName).stream()
                    .findFirst()
                    .orElseGet(() -> salesRepository.save(new SalesPerson(salesName)));
        }

        // 2. Resolve or create Project
        String customerName = req.customer().trim();
        final SalesPerson finalSalesPerson = salesPerson;
        Project project = projectRepository.findByCustomer(customerName).stream()
                .findFirst()
                .orElseGet(() -> projectRepository.save(new Project(customerName, finalSalesPerson)));

        // 3. Resolve or create Consultant
        String consultantName = req.consultant().trim();
        Consultant consultant = consultantRepository.findByName(consultantName).stream()
                .findFirst()
                .orElseGet(() -> consultantRepository.save(new Consultant(consultantName, finalSalesPerson, project)));

        // 4. Build and save Review
        Review review = new Review();
        review.setProject(project);
        review.setConsultant(consultant);
        review.setConsultantInformed(req.consultantInformed() != null ? req.consultantInformed() : false);

        // Scores
        review.setResultScore(req.resultScore());
        review.setResponsibilityScore(req.responsibilityScore());
        review.setSimplicityScore(req.simplicityScore());
        review.setJoyScore(req.joyScore());

        // Comments
        review.setResult(req.resultComment());
        review.setResponsibility(req.responsibilityComment());
        review.setSimplicity(req.simplicityComment());
        review.setJoy(req.joyComment());

        return reviewRepository.save(review);
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