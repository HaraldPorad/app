package com.example.demo.model.SalesPeopleModel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalesService {

    private final SalesRepository salesRepository;
    
    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public Optional<SalesPerson> getById(long id) {
        return salesRepository.findById(id);
    }

    public List<SalesPerson> getByName(String name) {
        return salesRepository.findByName(name);
    }

    @Transactional
    public void saveSalesPerson(SalesPerson salesPerson) {
        salesRepository.save(salesPerson);
    }

}
