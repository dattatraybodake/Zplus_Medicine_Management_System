package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import com.deesha.medicine_distribution.service.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
public class StateController {

    private final StateService service;

    @PostMapping("/savestate")
    public ResponseEntity<StateResponse> create(@Valid @RequestBody StateRequest req) {
        StateResponse created = service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.stateId()).toUri()).body(created);
    }

    @GetMapping("/viewallstates")
    public List<StateResponse> list(@RequestParam(required = false) Integer countryId) {
        return service.list(countryId);
    }

    @GetMapping("/viewstate/{id}")
    public StateResponse get(@PathVariable Integer id) { return service.get(id); }

    @PutMapping("/updatestate/{id}")
    public StateResponse update(@PathVariable Integer id, @Valid @RequestBody StateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("deletestate/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}