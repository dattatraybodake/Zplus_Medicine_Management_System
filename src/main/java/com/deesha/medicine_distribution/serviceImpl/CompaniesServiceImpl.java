package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.CompanyRequest;
import com.deesha.medicine_distribution.dto.CompanyDto.CompanyResponse;
import com.deesha.medicine_distribution.exception.ResourceNotFoundException;
import com.deesha.medicine_distribution.model.EnterpriseType;
import com.deesha.medicine_distribution.model.ManufactureCompany;
import com.deesha.medicine_distribution.repository.CompaniesRepository;
import com.deesha.medicine_distribution.repository.EnterpriseTypeRepository;
import com.deesha.medicine_distribution.service.CompaniesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CompaniesServiceImpl implements CompaniesService {

    private final CompaniesRepository companiesRepository;
    private final EnterpriseTypeRepository enterpriseTypeRepository;

    public CompaniesServiceImpl(
            CompaniesRepository companiesRepository,
            EnterpriseTypeRepository enterpriseTypeRepository) {

        this.companiesRepository = companiesRepository;
        this.enterpriseTypeRepository = enterpriseTypeRepository;
    }

    // ---------- CREATE ----------
    @Override
    public CompanyResponse create(CompanyRequest request) {

        if (companiesRepository.existsByCompanyNameIgnoreCaseAndDeletedAtIsNull(
                request.companyName())) {
            throw new IllegalArgumentException(
                    "Company already exists with name: " + request.companyName());
        }

        ManufactureCompany company = new ManufactureCompany();
        apply(company, request);
        company.setCreatedAt(LocalDateTime.now());

        return toResponse(companiesRepository.save(company));
    }

    // ---------- UPDATE ----------
    @Override
    public CompanyResponse update(Long companyId, CompanyRequest request) {

        ManufactureCompany company = companiesRepository
                .findByCompanyIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company not found with id: " + companyId));

        if (companiesRepository.existsByCompanyNameIgnoreCaseAndCompanyIdNotAndDeletedAtIsNull(
                request.companyName(), companyId)) {
            throw new IllegalArgumentException(
                    "Company name already in use: " + request.companyName());
        }

        apply(company, request);
        company.setModifiedBy(request.modifiedBy() != null
                ? request.modifiedBy() : request.createdBy());
        company.setModifiedAt(LocalDateTime.now());

        return toResponse(companiesRepository.save(company));
    }

    // ---------- READ ONE ----------
    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getById(Long companyId) {
        ManufactureCompany company = companiesRepository
                .findByCompanyIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company not found with id: " + companyId));
        return toResponse(company);
    }

    // ---------- READ ALL ----------
    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getAllCompanies() {
        return companiesRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(CompaniesServiceImpl::toResponse)
                .toList();
    }

    // ---------- READ ACTIVE ----------
    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getActiveCompanies() {
        return companiesRepository.findAllByActiveTrueAndDeletedAtIsNull()
                .stream()
                .map(CompaniesServiceImpl::toResponse)
                .toList();
    }

    // ---------- READ BY ENTERPRISE TYPE ----------
    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getByEnterpriseType(Integer enterpriseTypeId) {
        if (!enterpriseTypeRepository.existsById(enterpriseTypeId)) {
            throw new ResourceNotFoundException(
                    "Enterprise type not found: " + enterpriseTypeId);
        }
        return companiesRepository
                .findAllByEnterpriseType_EnterpriseTypeIdAndDeletedAtIsNull(enterpriseTypeId)
                .stream()
                .map(CompaniesServiceImpl::toResponse)
                .toList();
    }

    // ---------- SOFT DELETE ----------
    @Override
    public void delete(Long companyId) {
        ManufactureCompany company = companiesRepository
                .findByCompanyIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company not found with id: " + companyId));

        company.setActive(false);
        company.setDeletedAt(LocalDateTime.now());
        company.setDeletedBy("system");
        companiesRepository.save(company);
    }

    // ---------- USED BY CompanyAddressServiceImpl ----------
    @Override
    @Transactional(readOnly = true)
    public ManufactureCompany findActive(Long companyId) {
        return companiesRepository.findByCompanyIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active company not found with id: " + companyId));
    }

    // ---------- Helpers ----------
    private void apply(ManufactureCompany c, CompanyRequest r) {

        EnterpriseType et = enterpriseTypeRepository.findById(r.enterpriseTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enterprise type not found: " + r.enterpriseTypeId()));
        c.setEnterpriseType(et);

        c.setCompanyName(r.companyName().trim());
        c.setTradeName(r.tradeName());
        c.setIndustry(r.industry());
        c.setProducts(r.products());
        c.setWebsite(r.website());
        c.setCompanyStartDate(r.companyStartDate());
        c.setCompanyEndDate(r.companyEndDate());

        if (r.active() != null) {
            c.setActive(r.active());
        }
        if (c.getCompanyId() == null) {
            c.setCreatedBy(r.createdBy());
        }
    }

    static CompanyResponse toResponse(ManufactureCompany c) {
        EnterpriseType et = c.getEnterpriseType();

        return new CompanyResponse(
                c.getCompanyId(),
                et != null ? et.getEnterpriseTypeId() : null,
                et != null ? et.getEnterpriseType() : null,
                c.getCompanyName(),
                c.getTradeName(),
                c.getIndustry(),
                c.getProducts(),
                c.getWebsite(),
                c.isActive(),
                c.getCompanyStartDate(),
                c.getCompanyEndDate(),
                c.getCreatedBy(),
                c.getCreatedAt(),
                c.getModifiedBy(),
                c.getModifiedAt(),
                c.getDeletedBy(),
                c.getDeletedAt()
        );
    }
}