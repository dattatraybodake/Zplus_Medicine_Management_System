package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import com.deesha.medicine_distribution.service.DistrictService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService service;

    @PostMapping("/savedistrict")
    public ResponseEntity<DistrictResponse> create(@Valid @RequestBody DistrictRequest req) {
        DistrictResponse created = service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.districtId()).toUri()).body(created);
    }

    @GetMapping("/viewalldistrict")
    public List<DistrictResponse> list(@RequestParam(required = false) Integer stateId) {
        return service.list(stateId);
    }

    @GetMapping("viewdistrict/{id}")
    public DistrictResponse get(@PathVariable Integer id) { return service.get(id); }

    @PutMapping("update/{id}")
    public DistrictResponse update(@PathVariable Integer id, @Valid @RequestBody DistrictRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}