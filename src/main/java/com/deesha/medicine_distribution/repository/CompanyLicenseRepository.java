package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.CompanyLicenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyLicenseRepository extends JpaRepository<CompanyLicenses, Long> {

    // -------- List by company (non-deleted) --------
    List<CompanyLicenses> findByCompany_CompanyIdAndDeletedAtIsNull(Long companyId);

    // -------- Scoped fetch (ownership check) --------
    Optional<CompanyLicenses> findByLicenseIdAndCompany_CompanyIdAndDeletedAtIsNull(
            Long licenseId, Long companyId);

    // -------- Duplicate check --------
    boolean existsByCompany_CompanyIdAndLicenseTypeAndLicenseNumberIgnoreCaseAndDeletedAtIsNull(
            Long companyId,
            CompanyLicenses.LicenseType licenseType,
            String licenseNumber
    );

    boolean existsByCompany_CompanyIdAndLicenseTypeAndLicenseNumberIgnoreCaseAndLicenseIdNotAndDeletedAtIsNull(
            Long companyId,
            CompanyLicenses.LicenseType licenseType,
            String licenseNumber,
            Long licenseId
    );

    // -------- One type per company (uncomment if you need it) --------
    // boolean existsByCompany_CompanyIdAndLicenseTypeAndDeletedAtIsNull(
    //         Long companyId, CompanyLicenses.LicenseType licenseType);

    // -------- Expiring soon (within N days) --------
    @Query("SELECT l FROM CompanyLicenses l " +
            "WHERE l.deletedAt IS NULL " +
            "AND l.expiryDate BETWEEN :today AND :future")
    List<CompanyLicenses> findExpiringBetween(
            @Param("today") LocalDate today,
            @Param("future") LocalDate future);

    // -------- Already expired --------
    List<CompanyLicenses> findByExpiryDateBeforeAndDeletedAtIsNull(LocalDate date);
}