package com.example.demo.model.ReviewModel;

import java.time.LocalDate;

import com.example.demo.model.ConsultantModel.Consultant;
import com.example.demo.model.ProjectModel.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many reviews belong to One Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // Many reviews belong to One Consultant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

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
        Project project,
        Consultant consultant,
        
        Integer resultScore,
        Integer responsibilityScore,
        Integer simplicityScore,
        Integer joyScore,

        String result,
        String responsibility,
        String simplicity,
        String joy
    ) {
        this.project = project;
        this.consultant = consultant;

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
            "Review[id=%d, project='%s', consultant='%s',date='%s', consultantInformed='%s', result='%s',responsibility='%s',simplicity='%s', joy='%s']",
            id, project, consultant, date, consultantInformed, result, responsibility, simplicity, joy);
    }

    // Review getters
    public Long getId() { return id; }
    public Project getProject() { return project; }
    public Consultant getConsultant() { return consultant; }
    
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
    public void setProject(Project project) {this.project = project; }
    public void setConsultant(Consultant consultant) { this.consultant = consultant; }

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
