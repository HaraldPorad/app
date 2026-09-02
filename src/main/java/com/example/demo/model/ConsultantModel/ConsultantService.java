package com.example.demo.model.ConsultantModel;

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

    public List<Consultant> FilterByConsultantId(long consultantId) {
        return consultantRepository.findById(consultantId);
    }
    
    public List<Consultant> FilterByConsultantName(String consultantName) {
        return consultantRepository.findByName(consultantName);
    }

    public List<Consultant> FilterByManagerId(Long managerId) {
        return consultantRepository.findByManagerId(managerId);
    }

    
    
}
