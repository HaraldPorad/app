package com.example.demo.model.ConsultantModel;

public record ConsultantDto(
    Long id,
    String name,

    Long managerId,
    String managerName,

    Long projectId,
    String customer
) {
    public static ConsultantDto fromEntity(Consultant c) {
        Long mId = null;
        String mName = null;

        if (c.getManager() != null) {
            mId = c.getManager().getId();
            mName = c.getManager().getName();
        }

        Long pId = null;
        String pCustomer = null;
        if (c.getProject() != null) {
            pId = c.getProject().getId();
            pCustomer = c.getProject().getCustomer();
        }

        return new ConsultantDto(
            c.getId(),
            c.getName(),
            mId,
            mName,
            pId,
            pCustomer);
    }
}
