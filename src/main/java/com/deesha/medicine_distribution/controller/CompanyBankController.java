package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import com.deesha.medicine_distribution.service.CompanyBankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/bank-accounts")
public class CompanyBankController
{

    private final CompanyBankService bankAccountService;
    public CompanyBankController(CompanyBankService companyBankService)
    {
        this.bankAccountService = companyBankService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<BankAccountResponse> create(@PathVariable Long companyId, @RequestBody BankAccountRequest request)
    {
        BankAccountResponse bankAccountResponse = bankAccountService.create(companyId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountResponse);
    }

    @GetMapping(value = "/view-accounts")
    public ResponseEntity<List<BankAccountResponse>> getAll(@PathVariable Long companyId)
    {
        List<BankAccountResponse> accounts = bankAccountService.getAll(companyId);
        return ResponseEntity.ok(accounts);
    }
}