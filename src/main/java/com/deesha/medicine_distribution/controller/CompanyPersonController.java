package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import com.deesha.medicine_distribution.service.CompanyPersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/company-people")
@RequiredArgsConstructor
public class CompanyPersonController {

    private final CompanyPersonService service;

    @PostMapping("/save-person")
    public ResponseEntity<CompanyPersonResponse> create(
            @Valid @RequestBody CompanyPersonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyPersonResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CompanyPersonRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<CompanyPersonResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/getall-persons")
    public ResponseEntity<List<CompanyPersonResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<CompanyPersonResponse>> getByDepartment(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(service.getByDepartment(departmentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}