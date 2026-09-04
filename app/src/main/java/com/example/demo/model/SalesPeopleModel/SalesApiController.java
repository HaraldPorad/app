package com.example.demo.model.SalesPeopleModel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/sales")
public class SalesApiController {

    private final SalesService salesService;

    public SalesApiController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping
    public List<SalesPersonDto> getSalesPeople(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return salesService.getByName(name).stream()
                    .map(SalesPersonDto::fromEntity)
                    .toList();
        }
        return salesService.getAllSalesPeople().stream()
                .map(SalesPersonDto::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesPersonDto> getById(@PathVariable Long id) {
        return salesService.getById(id)
                .map(SalesPersonDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public record CreateSalesPersonRequest(String name) {}

    @PostMapping
    public ResponseEntity<SalesPersonDto> createSalesPerson(@RequestBody CreateSalesPersonRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        SalesPerson sp = new SalesPerson(request.name().trim());
        salesService.saveSalesPerson(sp);
        return ResponseEntity.status(HttpStatus.CREATED).body(SalesPersonDto.fromEntity(sp));
    }
    
}