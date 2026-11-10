package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import com.deesha.medicine_distribution.service.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService service;

    @PostMapping("savecountry")
    public ResponseEntity<CountryResponse> create(@RequestBody CountryRequest req) {
         CountryResponse created = service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.countryId()).toUri()).body(created);
    }

    @GetMapping("/viewallcountries")
    public List<CountryResponse> list() { return service.list(); }

    @GetMapping("/{id}")
    public CountryResponse get(@PathVariable Integer id) { return service.get(id); }

    @PutMapping("/{id}")
    public CountryResponse update(@PathVariable Integer id, @Valid @RequestBody CountryRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

