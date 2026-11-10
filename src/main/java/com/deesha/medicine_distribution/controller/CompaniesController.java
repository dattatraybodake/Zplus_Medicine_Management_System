package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.CompanyDto.CompanyRequest;
import com.deesha.medicine_distribution.dto.CompanyDto.CompanyResponse;
import com.deesha.medicine_distribution.service.CompaniesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private final CompaniesService companiesService;

    public CompaniesController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    // ---------- CREATE ----------
    @PostMapping(value = "save-company")
    public ResponseEntity<CompanyResponse> create(
            @Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companiesService.create(request));
    }

    // ---------- UPDATE ----------
    @PutMapping(value = "/update/{companyId}")
    public ResponseEntity<CompanyResponse> update(
            @PathVariable Long companyId,
            @Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.ok(companiesService.update(companyId, request));
    }

    // ---------- READ ONE ----------
    @GetMapping(value = "/get/{companyId}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable Long companyId) {
        return ResponseEntity.ok(companiesService.getById(companyId));
    }

    // ---------- READ ALL ----------
    @GetMapping(value = "/getall")
    public ResponseEntity<List<CompanyResponse>> getAll() {
        return ResponseEntity.ok(companiesService.getAllCompanies());
    }

    // ---------- READ ACTIVE ----------
    @GetMapping(value = "/get-active")
    public ResponseEntity<List<CompanyResponse>> getActive() {
        return ResponseEntity.ok(companiesService.getActiveCompanies());
    }

    // ---------- READ BY ENTERPRISE TYPE ----------
    @GetMapping(value = "/company-enterprise-type/{enterpriseTypeId}")
    public ResponseEntity<List<CompanyResponse>> getByEnterpriseType(
            @PathVariable Integer enterpriseTypeId) {
        return ResponseEntity.ok(companiesService.getByEnterpriseType(enterpriseTypeId));
    }

    // ---------- SOFT DELETE ----------
    @DeleteMapping(value = "/delete/{companyId}")
    public ResponseEntity<Void> delete(@PathVariable Long companyId) {
        companiesService.delete(companyId);
        return ResponseEntity.noContent().build();
    }
}