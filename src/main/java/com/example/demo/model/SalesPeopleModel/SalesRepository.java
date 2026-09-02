package com.example.demo.model.SalesPeopleModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<SalesPerson, Long>{

    List<SalesPerson> findById(long id);
    List<SalesPerson> findByName(String name);
}
