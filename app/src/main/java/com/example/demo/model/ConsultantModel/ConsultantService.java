package com.example.demo.model.ConsultantModel;

import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultantService {
    
    private final ConsultantRepository consultantRepository;

    public ConsultantService(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    @Transactional
    public void saveConsultant(Consultant consultant) {
        consultantRepository.save(consultant);
    }

    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAllWithDetails();
    }

    public Optional<Consultant> getByConsultantId(long consultantId) {
        return consultantRepository.findById(consultantId);
    }
    
    public List<Consultant> getByConsultantName(String consultantName) {
        return consultantRepository.findByName(consultantName);
    }

    public List<Consultant> getByManagerId(Long managerId) {
        return consultantRepository.findByManagerId(managerId);
    }

    
    
}
