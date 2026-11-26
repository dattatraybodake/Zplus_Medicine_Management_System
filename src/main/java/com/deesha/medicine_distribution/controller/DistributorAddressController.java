package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.model.DistributorAddress;
import com.deesha.medicine_distribution.service.DistributorAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/distributor-address")
@RequiredArgsConstructor
public class DistributorAddressController {

    private final DistributorAddressService distributorAddressService;

    @PostMapping("/save")
    public ResponseEntity<DistributorAddress> createDistributorAddress(@Valid @RequestBody DistributorAddress distributorAddress) {
        DistributorAddress savedAddress = distributorAddressService.createDistributorAddress(distributorAddress);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @GetMapping("/getaddressbyid/{id}")
    public ResponseEntity<DistributorAddress> getDistributorAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(distributorAddressService.getDistributorAddressById(id));
    }

    @GetMapping("/getalladdresses")
    public ResponseEntity<List<DistributorAddress>> getAllDistributorAddresses() {
        return ResponseEntity.ok(distributorAddressService.getAllDistributorAddresses());
    }

    @GetMapping("/distributor/{distributorId}")
    public ResponseEntity<List<DistributorAddress>> getAddressesByDistributorId(@PathVariable Long distributorId) {
        return ResponseEntity.ok(distributorAddressService.getAddressesByDistributorId(distributorId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DistributorAddress> updateDistributorAddress(@PathVariable Long id, @Valid @RequestBody DistributorAddress distributorAddress) {
        return ResponseEntity.ok(distributorAddressService.updateDistributorAddress(id, distributorAddress));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDistributorAddress(@PathVariable Long id) {
        distributorAddressService.deleteDistributorAddress(id);
        return ResponseEntity.noContent().build();
    }
}
