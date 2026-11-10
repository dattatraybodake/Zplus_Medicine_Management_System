package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import com.deesha.medicine_distribution.model.CompanyBankAccount;
import com.deesha.medicine_distribution.model.ManufactureCompany;
import com.deesha.medicine_distribution.repository.CompanyBankRepository;
import com.deesha.medicine_distribution.service.CompanyBankService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
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
    public BankAccountResponse create(Long companyId, BankAccountRequest bankAccountRequest) {
        ManufactureCompany company = companiesService.findActive(companyId);
        CompanyBankAccount account =  new CompanyBankAccount();
        account.setCompany(company);

        apply(account, bankAccountRequest);
        CompanyBankAccount savedAccount = accountRepository.save(account);
        return toResponse(savedAccount);
    }

    @Override
    public List<BankAccountResponse> getAll(Long companyId)
    {
        companiesService.findActive(companyId);
        return accountRepository
                .findByCompany_CompanyId(companyId)
                .stream()
                .map(CompanyBankServiceImpl::toResponse)
                .toList();
    }

    @Override
    public BankAccountResponse findById(Long companyId, Long bankAccountId) {
        return null;
    }

    @Override
    public BankAccountResponse update(BankAccountRequest bankAccountRequest) {
        return null;
    }

    @Override
    public void delete(Integer companyId, Long bankAccountId) {

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
        return new BankAccountResponse(b.getBankAccountId(), b.getCompany(),
                b.getBankName(), b.getAccountName(), b.getAccountNumber(),
                b.getIfscCode(), b.getBranchName(), b.getActive());
    }
}
