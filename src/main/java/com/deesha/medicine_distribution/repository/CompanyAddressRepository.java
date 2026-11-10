package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.CompanyAddress;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompanyAddressRepository extends JpaRepository<CompanyAddress,Long> {

    // FIX: use nested property traversal `Company_CompanyId`
    List<CompanyAddress> findByCompany_CompanyId(Long companyId);

    // Scoped fetch — ensures address belongs to the given company
    Optional<CompanyAddress> findByAddressIdAndCompany_CompanyId(Long addressId, Long companyId);

    boolean existsByCompany_CompanyIdAndAddressType(
            Long companyId,
            CompanyAddress.AddressType addressType
    );

    // For update — exclude current record
    boolean existsByCompany_CompanyIdAndAddressTypeAndAddressIdNot(
            Long companyId,
            CompanyAddress.AddressType addressType,
            Long addressId
    );
}