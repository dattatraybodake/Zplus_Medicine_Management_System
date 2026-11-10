package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.CompanyDto.LicenseRequest;
import com.deesha.medicine_distribution.dto.CompanyDto.LicenseResponse;
import com.deesha.medicine_distribution.service.CompanyLicenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/licenses")
public class CompanyLicenseController {

    private final CompanyLicenseService service;

    public CompanyLicenseController(CompanyLicenseService service) {
        this.service = service;
    }

    // ---------- CREATE ----------
    @PostMapping(value = "/save")
    public ResponseEntity<LicenseResponse> create(
            @PathVariable Long companyId,
            @Valid @RequestBody LicenseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(companyId, request));
    }

    // ---------- LIST ----------
    @GetMapping(value = "getallcompanies")
    public ResponseEntity<List<LicenseResponse>> getAll(@PathVariable Long companyId) {
        return ResponseEntity.ok(service.getAll(companyId));
    }

    // ---------- GET ONE ----------
    @GetMapping("/getone/{licenseId}")
    public ResponseEntity<LicenseResponse> getById(
            @PathVariable Long companyId,
            @PathVariable Long licenseId) {
        return ResponseEntity.ok(service.getById(companyId, licenseId));
    }

    // ---------- UPDATE ----------
    @PutMapping("/update/{licenseId}")
    public ResponseEntity<LicenseResponse> update(
            @PathVariable Long companyId,
            @PathVariable Long licenseId,
            @Valid @RequestBody LicenseRequest request) {
        return ResponseEntity.ok(service.update(companyId, licenseId, request));
    }

    // ---------- DELETE ----------
    @DeleteMapping("/delete/{licenseId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long companyId,
            @PathVariable Long licenseId) {
        service.delete(companyId, licenseId);
        return ResponseEntity.noContent().build();
    }

    // ---------- EXPIRING SOON ----------
    @GetMapping("/expiring")
    public ResponseEntity<List<LicenseResponse>> expiring(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(service.getExpiringSoon(days));
    }

    // ---------- EXPIRED ----------
    @GetMapping("/expired")
    public ResponseEntity<List<LicenseResponse>> expired() {
        return ResponseEntity.ok(service.getExpired());
    }
}