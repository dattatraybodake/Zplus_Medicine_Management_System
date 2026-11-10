package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.CompanyDto.LicenseRequest;
import com.deesha.medicine_distribution.dto.CompanyDto.LicenseResponse;

import java.util.List;

public interface CompanyLicenseService {

    LicenseResponse create(Long companyId, LicenseRequest request);

    List<LicenseResponse> getAll(Long companyId);

    LicenseResponse getById(Long companyId, Long licenseId);

    LicenseResponse update(Long companyId, Long licenseId, LicenseRequest request);

    void delete(Long companyId, Long licenseId);

    // Extras (useful for compliance)
    List<LicenseResponse> getExpiringSoon(int days);

    List<LicenseResponse> getExpired();
}