package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.CompanyBankAccount;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyBankRepository extends JpaRepository<CompanyBankAccount, Long>
{
    List<CompanyBankAccount> findByCompany_CompanyId(Long companyId);
    Optional<CompanyBankAccount> findByBankAccountIdAndCompany_CompanyId(Long bankAccountId, Integer companyId);
    boolean existsByAccountNumber(String accountNumber);
}