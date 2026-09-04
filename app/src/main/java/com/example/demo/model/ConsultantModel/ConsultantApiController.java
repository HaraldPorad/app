package com.example.demo.model.ConsultantModel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ProjectModel.Project;
import com.example.demo.model.SalesPeopleModel.SalesPerson;

@RestController
@RequestMapping("/api/consultants")
public class ConsultantApiController {

    private final ConsultantService consultantService;

    public ConsultantApiController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @GetMapping
    public List<ConsultantDto> getConsultants(@RequestParam(required = false) Long salesPersonId) {
        if (salesPersonId != null) {
            return consultantService.getByManagerId(salesPersonId).stream()
                    .map(ConsultantDto::fromEntity)
                    .toList();
        }
        return consultantService.getAllConsultants().stream()
                .map(ConsultantDto::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultantDto> getConsultantById(@PathVariable Long id) {
        return consultantService.getByConsultantId(id)
                .map(ConsultantDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    public record CreateConsultantRequest(String name, SalesPerson salesPerson, Project project) {} 

    @PostMapping
    public ResponseEntity<ConsultantDto> createConsultant(@RequestBody CreateConsultantRequest request) {
        if (request.name.trim() == null || request.name.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Consultant c = new Consultant(request.name.trim(), request.salesPerson(), request.project());
        consultantService.saveConsultant(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(ConsultantDto.fromEntity(c));
    }


}