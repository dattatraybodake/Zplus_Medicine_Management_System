package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.model.EnterpriseType;
import com.deesha.medicine_distribution.service.EnterpriseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enterprise-type")
@RequiredArgsConstructor
public class EnterpriseTypeController {

    private final EnterpriseTypeService service;

    @PostMapping(value = "save")
    public ResponseEntity<EnterpriseType> create(@RequestBody EnterpriseType enterpriseType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(enterpriseType));
    }

    @GetMapping(value = "getall")
    public ResponseEntity<List<EnterpriseType>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "getbyid/{id}")
    public ResponseEntity<EnterpriseType> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping(value = "updatebyid/{id}")
    public ResponseEntity<EnterpriseType> update(@PathVariable Integer id, @RequestBody EnterpriseType enterpriseType) {
        return ResponseEntity.ok(service.update(id, enterpriseType));
    }

    @DeleteMapping(value = "deletebyid/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}