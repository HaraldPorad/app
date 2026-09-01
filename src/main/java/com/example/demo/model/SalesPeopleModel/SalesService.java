package com.example.demo.model.SalesPeopleModel;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalesService {

    private final SalesRepository salesRepository;
    
    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Transactional
    public void saveSalesPerson(SalesPerson salesPerson) {
        salesRepository.save(salesPerson);
    }

    public List<SalesPerson> FilterById(long id) {
        return salesRepository.findById(id);
    }

    public List<SalesPerson> FilterByName(String name) {
        return salesRepository.findByName(name);
    }
}
