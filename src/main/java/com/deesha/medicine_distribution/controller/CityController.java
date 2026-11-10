package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import com.deesha.medicine_distribution.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService service;

    @PostMapping("/savecity")
    public ResponseEntity<CityResponse> create(@Valid @RequestBody CityRequest req) {
        CityResponse created = service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.citiesId()).toUri()).body(created);
    }

    @GetMapping("/viewallcities")
    public List<CityResponse> list(@RequestParam(required = false) Integer districtId) {
        return service.list(districtId);
    }

    @GetMapping("/view/{id}")
    public CityResponse get(@PathVariable Integer id) { return service.get(id); }

    @PutMapping("update/{id}")
    public CityResponse update(@PathVariable Integer id, @Valid @RequestBody CityRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}