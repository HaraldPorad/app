package com.example.demo.model.ProjectModel;

public record ProjectDto(
    Long id,
    String customer,
    
    // flattened sales relational field
    Long salesPersonId,
    String salesPersonName

) {

   public static ProjectDto fromEntity(Project p) {
    Long spId = null;
    String spName = null;

    if (p.getSalesPerson() != null) {
        spId = p.getSalesPerson().getId();
        spName = p.getSalesPerson().getName();
    }
    return new ProjectDto(
        p.getId(), 
        p.getCustomer(),
        spId,
        spName
        );
   } 
}
