package com.example.demo.model.ReviewModel;

import java.time.LocalDate;

public record CreateReviewRequest(
    String customer,
    String consultant,
    String salesPerson,
    LocalDate date,
    Boolean consultantInformed,
    Integer resultScore,
    String resultComment,
    Integer responsibilityScore,
    String responsibilityComment,
    Integer simplicityScore,
    String simplicityComment,
    Integer joyScore,
    String joyComment
) {}