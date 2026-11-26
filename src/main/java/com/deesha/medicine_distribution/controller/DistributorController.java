package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.model.Distributor;
import com.deesha.medicine_distribution.service.DistributorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/distributors")
@RequiredArgsConstructor
public class DistributorController
{
    private final DistributorService service;

    @PostMapping("/save")
    public ResponseEntity<Distributor> create(@Valid @RequestBody Distributor distributor) {
        Distributor saved = service.create(distributor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getDistributorId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/get/{id}")
    public Distributor getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/getall")
    public Page<Distributor> getAll(@RequestParam(required = false) Distributor.Status status,
                                    @RequestParam(required = false) String name,
                                    @PageableDefault(size = 20, sort = "distributorName", direction = Sort.Direction.ASC)
                                        Pageable pageable) {
        return service.getAll(status, name, pageable);
    }

    @PutMapping("/update/{id}")
    public Distributor update(@PathVariable Long id, @Valid @RequestBody Distributor distributor) {
        return service.update(id, distributor);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, @RequestParam(defaultValue = "system") String deletedBy) {
        service.delete(id, deletedBy);
    }
}