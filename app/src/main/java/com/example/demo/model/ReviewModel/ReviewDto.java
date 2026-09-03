package com.example.demo.model.ReviewModel;

import java.time.LocalDate;

public record ReviewDto(
    Long id,
    LocalDate date,
    Boolean consultantInformed,
    
    // Flattened relational fields
    Long projectId,
    String customer,
    Long consultantId,
    String consultantName,
    String salesPersonName,

    // Numerical scores
    Integer resultScore,
    Integer responsibilityScore,
    Integer simplicityScore,
    Integer joyScore,

    // Text feedback
    String resultComment,
    String responsibilityComment,
    String simplicityComment,
    String joyComment
) {
    public static ReviewDto fromEntity(Review r) {
        String salesName = null;
        if (r.getProject() != null && r.getProject().getSalesPerson() != null) {
            salesName = r.getProject().getSalesPerson().getName();
        }

    return new ReviewDto(
                r.getId(),
                r.getDate(),
                r.getConsultantInformed(),
                r.getProject() != null ? r.getProject().getId() : null,
                r.getProject() != null ? r.getProject().getCustomer() : null,
                r.getConsultant() != null ? r.getConsultant().getId() : null,
                r.getConsultant() != null ? r.getConsultant().getName() : null,
                salesName,
                r.getResultScore(),
                r.getResponsibilityScore(),
                r.getSimplicityScore(),
                r.getJoyScore(),
                r.getResult(),
                r.getResponsibility(),
                r.getSimplicity(),
                r.getJoy()
            );

        }
    }
