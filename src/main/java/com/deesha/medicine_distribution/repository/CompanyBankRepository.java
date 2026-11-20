package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.CompanyBankAccount;
import java.util.Optional;   // ✅ correct
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyBankRepository extends JpaRepository<CompanyBankAccount, Long>
{
    List<CompanyBankAccount> findByCompany_CompanyId(Long companyId);

    Optional<CompanyBankAccount> findByBankAccountIdAndCompany_CompanyId(
            Long bankAccountId, Long companyId);

    boolean existsByCompany_CompanyIdAndAccountNumber(
            Long companyId, String accountNumber);

    boolean existsByCompany_CompanyIdAndAccountNumberAndBankAccountIdNot(
            Long companyId, String accountNumber, Long bankAccountId);
}