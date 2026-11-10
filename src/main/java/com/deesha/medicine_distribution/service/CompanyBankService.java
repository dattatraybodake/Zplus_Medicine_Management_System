package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import java.util.List;

public interface CompanyBankService {

    BankAccountResponse create (Long companyId, BankAccountRequest bankAccountRequest);
    List<BankAccountResponse> getAll(Long companyId);
    BankAccountResponse findById(Long companyId, Long bankAccountId);
    BankAccountResponse update(BankAccountRequest bankAccountRequest);
    void delete(Integer companyId, Long bankAccountId);
}
