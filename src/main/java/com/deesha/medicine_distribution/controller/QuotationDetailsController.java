//package com.deesha.medicine_distribution.controller;
//
//import com.deesha.medicine_distribution.dto.QuotationDto.QuatationDetailsRequest;
//import com.deesha.medicine_distribution.dto.QuotationDto.QuatationDetailsResponse;
//import com.deesha.medicine_distribution.service.QuotationDetailsService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Positive;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//import java.util.List;
//
//@RestController
//@RequestMapping("quotations")
//@Validated
//public class QuotationDetailsController {
//
//    private final QuotationDetailsService service;
//
//    public QuotationDetailsController(QuotationDetailsService service) {
//        this.service = service;
//    }
//
//    // ---------- Create ----------
//    @PostMapping("/save")
//    public ResponseEntity<QuatationDetailsResponse> create(
//            @PathVariable @Positive(message = "quotationId must be positive") Long quotationId,
//            @Valid @RequestBody QuatationDetailsRequest request) {
//        QuatationDetailsResponse created = service.create(quotationId, request);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/api/quotations/details/{id}")
//                .buildAndExpand(created.quotationDetailsId())
//                .toUri();
//
//        return ResponseEntity.created(location).body(created);
//        }
//
//
//
//// ---------- Read one ----------
//// GET /api/quotations/details/{quotationDetailsId}
//@GetMapping("/details/{quotationDetailsId}")
//public ResponseEntity<QuatationDetailsResponse> getById(@PathVariable @Positive(message = "quotationDetailsId must be positive") Long quotationDetailsId) {
//
//    return ResponseEntity.ok(service.getById(quotationDetailsId));
//}
//
//// ---------- Read all for a quotation ----------
//// GET /api/quotations/{quotationId}/details
//@GetMapping("/viewall")
//public ResponseEntity<List<QuatationDetailsResponse>> getAllByQuotationId(
//        @PathVariable @Positive(message = "quotationId must be positive") Long quotationId) {
//
//    return ResponseEntity.ok(service.getAllByQuotationId(quotationId));
//}
//}