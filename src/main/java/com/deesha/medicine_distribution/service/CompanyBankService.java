package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import java.util.List;

public interface CompanyBankService {

    BankAccountResponse create (Long companyId, BankAccountRequest request);
    List<BankAccountResponse> getAll(Long companyId);
    BankAccountResponse findById(Long companyId, Long bankAccountId);
    BankAccountResponse update(Long companyId, Long bankAccountId, BankAccountRequest request);
    void delete(Long companyId, Long bankAccountId);
}
