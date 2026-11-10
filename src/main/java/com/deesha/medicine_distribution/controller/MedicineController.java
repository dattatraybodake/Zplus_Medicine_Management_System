//package com.deesha.medicine_distribution.controller;
//
//import com.deesha.medicine_distribution.dto.CompanyDto.*;
//import com.deesha.medicine_distribution.service.MedicineService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/medicines")
//@RequiredArgsConstructor
//public class MedicineController {
//
//    private final MedicineService service;
//
//    @PostMapping("/save")
//    public ResponseEntity<MedicineResponse> create(@Valid @RequestBody MedicineRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
//    }
//
//    @GetMapping("/viewall")
//    public ResponseEntity<List<MedicineResponse>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }
//
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<List<MedicineResponse>> getByCategory(@PathVariable Integer categoryId) {
//        return ResponseEntity.ok(service.getByCategory(categoryId));
//    }
//
//    @GetMapping("/view/{id}")
//    public ResponseEntity<MedicineResponse> getById(@PathVariable Long id) {
//        return ResponseEntity.ok(service.getById(id));
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<MedicineResponse> update(@PathVariable Long id, @Valid @RequestBody MedicineRequest request) {
//        return ResponseEntity.ok(service.update(id, request));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam(required = false) String modifiedBy) {
//        service.delete(id, modifiedBy);
//        return ResponseEntity.noContent().build();
//    }
//}