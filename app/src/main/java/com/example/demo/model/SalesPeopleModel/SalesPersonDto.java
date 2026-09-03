package com.example.demo.model.SalesPeopleModel;

public record SalesPersonDto(
    Long id,
    String name
) {
    public static SalesPersonDto fromEntity(SalesPerson sp) {
        return new SalesPersonDto(
            sp.getId(),
            sp.getName()
        );
    }
}