package com.example.demo.model.ReviewModel;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long projectId;
    private Long customerId;
    private Long consultantId;  

    private final LocalDate date = LocalDate.now();
    private Boolean consultantInformed = false;

    private Integer resultScore;
    private Integer responsibilityScore;
    private Integer simplicityScore;
    private Integer joyScore;

    private String result;
    private String responsibility;
    private String simplicity;
    private String joy;

    public Review () {}

        public Review(
        Long projectId,
        Long customerId,
        Long consultantId,
        
        Integer resultScore,
        Integer responsibilityScore,
        Integer simplicityScore,
        Integer joyScore,

        String result,
        String responsibility,
        String simplicity,
        String joy
    ) {
        this.projectId = projectId;
        this.customerId = customerId;
        this.consultantId = consultantId;

        this.resultScore = resultScore;
        this.responsibilityScore = responsibilityScore;
        this.simplicityScore = simplicityScore;
        this.joyScore = joyScore;

        this.result = result;
        this.responsibility = responsibility;
        this.simplicity = simplicity;
        this.joy = joy;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Review[id=%d, projectId='%s', date='%s', consultantInformed='%s', result='%s',responsibility='%s',simplicity='%s', joy='%s']",
            id, projectId, date, consultantInformed, result, responsibility, simplicity, joy);
    }

    // Review getters
    public Long getId() { return id; }
    public Long getProjectId() { return projectId; }
    public Long getConsultantId() { return consultantId; }
    public Long getCustomerId() { return customerId; }
    
    public LocalDate getDate() { return date; }
    public Boolean getConsultantInformed() { return consultantInformed != null && consultantInformed; }

    public Integer getResultScore () { return resultScore; }
    public Integer getResponsibilityScore () { return responsibilityScore; }
    public Integer getSimplicityScore () { return simplicityScore; }
    public Integer getJoyScore () { return joyScore; }
   
    public String getResult() { return result; }
    public String getResponsibility() { return responsibility; }
    public String getSimplicity() { return simplicity; }
    public String getJoy() { return joy; }

    // Review setters
    public void setId(Long id) { this.id = id; }
    public void setProjectId(Long projectId) {this.projectId = projectId; }
    public void setConsultantId(Long consultantId) { this.consultantId = consultantId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public void setConsultantInformed(Boolean setConsultantInformed) { this.consultantInformed = (consultantInformed != null) ? consultantInformed : false; }

    public void setResultScore(Integer resultScore) {this.resultScore = resultScore; }
    public void setResponsibilityScore(Integer responsibilityScore) {this.responsibilityScore = responsibilityScore; }
    public void setSimplicityScore(Integer simplicityScore) {this.simplicityScore = simplicityScore; }
    public void setJoyScore(Integer joyScore) {this.joyScore = joyScore; }

    public void setResult(String result) { this.result = result; }
    public void setResponsibility(String responsibility) { this.responsibility = responsibility; }
    public void setSimplicity(String simplicity) { this.simplicity = simplicity; }
    public void setJoy(String joy) { this.joy = joy; }

}
