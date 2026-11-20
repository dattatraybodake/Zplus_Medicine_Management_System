package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import com.deesha.medicine_distribution.model.CompanyBankAccount;
import com.deesha.medicine_distribution.model.ManufactureCompany;
import com.deesha.medicine_distribution.repository.CompanyBankRepository;
import com.deesha.medicine_distribution.service.CompanyBankService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;   // ✅import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CompanyBankServiceImpl implements CompanyBankService {

    private final CompanyBankRepository accountRepository;
    private final CompaniesServiceImpl companiesService;

    public CompanyBankServiceImpl(CompanyBankRepository accountRepository,  CompaniesServiceImpl companiesService) {
        this.accountRepository = accountRepository;
        this.companiesService = companiesService;
    }

    @Override
    public BankAccountResponse create(Long companyId, BankAccountRequest request) {
        ManufactureCompany company = companiesService.findActive(companyId);

        // (company, accountNumber) must be unique
        if (accountRepository.existsByCompany_CompanyIdAndAccountNumber(
                companyId, request.accountNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Account number already exists for this company");
        }

        CompanyBankAccount account = new CompanyBankAccount();
        account.setCompany(company);
        apply(account, request);
        return toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS) // read-only
    public List<BankAccountResponse> getAll(Long companyId)
    {
        companiesService.findActive(companyId);
        return accountRepository.findByCompany_CompanyId(companyId)
                .stream()
                .map(CompanyBankServiceImpl::toResponse)
                .toList();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public BankAccountResponse findById(Long companyId, Long bankAccountId) {
        return toResponse(loadOwned(companyId, bankAccountId));
    }

    @Override
    public BankAccountResponse update(Long companyId, Long bankAccountId, BankAccountRequest request) {

        CompanyBankAccount account = loadOwned(companyId, bankAccountId);

        if(accountRepository.existsByCompany_CompanyIdAndAccountNumberAndBankAccountIdNot(companyId, request.accountNumber(), bankAccountId))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account number already exists for this company");
        }

        apply(account, request);
        return toResponse(accountRepository.save(account));
    }

    @Override
    public void delete(Long companyId, Long bankAccountId) {
        CompanyBankAccount account = loadOwned(companyId, bankAccountId);
        accountRepository.delete(account);
    }


    private CompanyBankAccount loadOwned(Long companyId, Long bankAccountId) {
        return accountRepository
                .findByBankAccountIdAndCompany_CompanyId(bankAccountId, companyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bank account " + bankAccountId + " not found for company " + companyId));
    }

    private void apply(CompanyBankAccount b, BankAccountRequest r) {
        b.setBankName(r.bankName());
        b.setAccountName(r.accountName());
        b.setAccountNumber(r.accountNumber());
        b.setIfscCode(r.ifscCode());
        b.setBranchName(r.branchName());
        if (r.isActive() != null) b.setActive(r.isActive());
    }

    static BankAccountResponse toResponse(CompanyBankAccount b) {
        return new BankAccountResponse(
                b.getBankAccountId(),               // Long
                b.getCompany().getCompanyId(),      // Long  ✅
                b.getBankName(),
                b.getAccountName(),
                b.getAccountNumber(),
                b.getIfscCode(),
                b.getBranchName(),
                b.getActive()
        );
    }
}
