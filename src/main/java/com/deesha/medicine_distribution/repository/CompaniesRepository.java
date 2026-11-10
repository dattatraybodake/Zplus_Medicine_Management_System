package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.ManufactureCompany;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompaniesRepository extends JpaRepository<ManufactureCompany, Long> {

    // -------- List with enterprise type preloaded --------
    @EntityGraph(attributePaths = {"enterpriseType"})
    List<ManufactureCompany> findAllByDeletedAtIsNull();

    // -------- Fetch one with enterprise type + collections (single query) --------
    @Query("SELECT DISTINCT c FROM ManufactureCompany c " +
            "LEFT JOIN FETCH c.enterpriseType " +
            "LEFT JOIN FETCH c.addresses a " +
            "LEFT JOIN FETCH a.city ci " +
            "LEFT JOIN FETCH ci.district d " +
            "LEFT JOIN FETCH d.state s " +
            "LEFT JOIN FETCH s.country " +
            "LEFT JOIN FETCH c.bankAccounts " +
            "LEFT JOIN FETCH c.licenses " +
            "WHERE c.companyId = :id AND c.deletedAt IS NULL")
    Optional<ManufactureCompany> findDetailById(@Param("id") Long id);

    // -------- Simple lookups --------
    Optional<ManufactureCompany> findByCompanyIdAndActiveTrue(Long companyId);

    @EntityGraph(attributePaths = "enterpriseType")
    Optional<ManufactureCompany> findByCompanyIdAndDeletedAtIsNull(Long companyId);

    // -------- Duplicate check --------
    boolean existsByCompanyNameIgnoreCaseAndDeletedAtIsNull(String companyName);

    boolean existsByCompanyNameIgnoreCaseAndCompanyIdNotAndDeletedAtIsNull(
            String companyName, Long companyId
    );

    // -------- Search by status / type --------
    List<ManufactureCompany> findAllByActiveTrueAndDeletedAtIsNull();

    List<ManufactureCompany> findAllByEnterpriseType_EnterpriseTypeIdAndDeletedAtIsNull(
            Integer enterpriseTypeId
    );
}