package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.CompanyDto.AddressRequest;
import com.deesha.medicine_distribution.dto.CompanyDto.AddressResponse;
import com.deesha.medicine_distribution.service.CompanyAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/company-address")
public class CompanyAddressController {

    private final CompanyAddressService service;

    public CompanyAddressController(CompanyAddressService service) {
        this.service = service;
    }

    // ---------- CREATE ----------
    @PostMapping(value = "/save")
    public ResponseEntity<AddressResponse> create(
            @PathVariable Long companyId,
            @Valid @RequestBody AddressRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(companyId, request));
    }

    // ---------- LIST ----------
    @GetMapping(value = "/getall")
    public ResponseEntity<List<AddressResponse>> getAll(@PathVariable Long companyId) {
        return ResponseEntity.ok(service.getAll(companyId));
    }

    // ---------- GET ONE ----------
    @GetMapping(value = "/getbyid/{addressId}")
    public ResponseEntity<AddressResponse> getById(
            @PathVariable Long companyId,
            @PathVariable Long addressId) {

        return ResponseEntity.ok(service.getById(companyId, addressId));
    }

    // ---------- UPDATE ----------
    @PutMapping(value = "/update/{addressId}")
    public ResponseEntity<AddressResponse> update(
            @PathVariable Long companyId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request) {

        return ResponseEntity.ok(service.update(companyId, addressId, request));
    }

    // ---------- DELETE ----------
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long companyId,
            @PathVariable Long addressId) {

        service.delete(companyId, addressId);
        return ResponseEntity.noContent().build();
    }
}