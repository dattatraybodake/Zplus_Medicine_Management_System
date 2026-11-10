package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.LicenseRequest;
import com.deesha.medicine_distribution.dto.CompanyDto.LicenseResponse;
import com.deesha.medicine_distribution.exception.ResourceNotFoundException;
import com.deesha.medicine_distribution.model.CompanyLicenses;
import com.deesha.medicine_distribution.model.ManufactureCompany;
import com.deesha.medicine_distribution.repository.CompanyLicenseRepository;
import com.deesha.medicine_distribution.service.CompaniesService;
import com.deesha.medicine_distribution.service.CompanyLicenseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CompanyLicenseServiceImpl implements CompanyLicenseService {

    private final CompanyLicenseRepository licenseRepository;
    private final CompaniesService companiesService;

    public CompanyLicenseServiceImpl(
            CompanyLicenseRepository licenseRepository,
            CompaniesService companiesService) {

        this.licenseRepository = licenseRepository;
        this.companiesService = companiesService;
    }

    // ---------- CREATE ----------
    @Override
    public LicenseResponse create(Long companyId, LicenseRequest request) {

        ManufactureCompany company = companiesService.findActive(companyId);

        if (licenseRepository
                .existsByCompany_CompanyIdAndLicenseTypeAndLicenseNumberIgnoreCaseAndDeletedAtIsNull(
                        companyId, request.licenseType(), request.licenseNumber())) {
            throw new IllegalArgumentException(
                    "License " + request.licenseNumber()
                            + " of type " + request.licenseType()
                            + " already exists for company " + companyId);
        }

        CompanyLicenses license = new CompanyLicenses();
        license.setCompany(company);
        apply(license, request);

        return toResponse(licenseRepository.save(license));
    }

    // ---------- READ ALL ----------
    @Override
    @Transactional(readOnly = true)
    public List<LicenseResponse> getAll(Long companyId) {
        companiesService.findActive(companyId);
        return licenseRepository
                .findByCompany_CompanyIdAndDeletedAtIsNull(companyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ---------- READ ONE ----------
    @Override
    @Transactional(readOnly = true)
    public LicenseResponse getById(Long companyId, Long licenseId) {
        CompanyLicenses license = licenseRepository
                .findByLicenseIdAndCompany_CompanyIdAndDeletedAtIsNull(licenseId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "License " + licenseId + " not found for company " + companyId));
        return toResponse(license);
    }

    // ---------- UPDATE ----------
    @Override
    public LicenseResponse update(Long companyId, Long licenseId, LicenseRequest request) {

        CompanyLicenses license = licenseRepository
                .findByLicenseIdAndCompany_CompanyIdAndDeletedAtIsNull(licenseId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "License " + licenseId + " not found for company " + companyId));

        // Duplicate check if type or number changed
        if ((!license.getLicenseType().equals(request.licenseType())
                || !license.getLicenseNumber().equalsIgnoreCase(request.licenseNumber()))
                && licenseRepository
                .existsByCompany_CompanyIdAndLicenseTypeAndLicenseNumberIgnoreCaseAndLicenseIdNotAndDeletedAtIsNull(
                        companyId, request.licenseType(), request.licenseNumber(), licenseId)) {
            throw new IllegalArgumentException(
                    "License " + request.licenseNumber()
                            + " of type " + request.licenseType()
                            + " already exists for company " + companyId);
        }

        apply(license, request);
        license.setModifiedBy(request.createdBy());
        license.setModifiedAt(LocalDateTime.now());

        return toResponse(licenseRepository.save(license));
    }

    // ---------- SOFT DELETE ----------
    @Override
    public void delete(Long companyId, Long licenseId) {
        CompanyLicenses license = licenseRepository
                .findByLicenseIdAndCompany_CompanyIdAndDeletedAtIsNull(licenseId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "License " + licenseId + " not found for company " + companyId));

        license.setDeletedAt(LocalDateTime.now());
        license.setDeletedBy("system");
        licenseRepository.save(license);
    }

    // ---------- EXPIRING SOON ----------
    @Override
    @Transactional(readOnly = true)
    public List<LicenseResponse> getExpiringSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate future = today.plusDays(days);
        return licenseRepository.findExpiringBetween(today, future)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ---------- EXPIRED ----------
    @Override
    @Transactional(readOnly = true)
    public List<LicenseResponse> getExpired() {
        return licenseRepository.findByExpiryDateBeforeAndDeletedAtIsNull(LocalDate.now())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ---------- Helpers ----------
    private void apply(CompanyLicenses license, LicenseRequest request) {
        license.setLicenseType(request.licenseType());
        license.setLicenseNumber(request.licenseNumber().trim());
        license.setIssueDate(request.issueDate());
        license.setExpiryDate(request.expiryDate());

        if (license.getLicenseId() == null) {
            license.setCreatedBy(request.createdBy());
            license.setCreatedAt(LocalDateTime.now());
        }
    }

    private LicenseResponse toResponse(CompanyLicenses l) {
        return new LicenseResponse(
                l.getLicenseId(),
                l.getCompany() != null ? l.getCompany().getCompanyId() : null,
                l.getCompany() != null ? l.getCompany().getCompanyName() : null,
                l.getLicenseType(),
                l.getLicenseNumber(),
                l.getIssueDate(),
                l.getExpiryDate(),
                l.getCreatedBy(),
                l.getCreatedAt(),
                l.getModifiedBy(),
                l.getModifiedAt()
        );
    }
}