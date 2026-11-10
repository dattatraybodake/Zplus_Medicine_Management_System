package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.CompanyDto.CompanyRequest;
import com.deesha.medicine_distribution.dto.CompanyDto.CompanyResponse;
import com.deesha.medicine_distribution.model.ManufactureCompany;

import java.util.List;

public interface CompaniesService {

    CompanyResponse create(CompanyRequest request);

    CompanyResponse update(Long companyId, CompanyRequest request);

    CompanyResponse getById(Long companyId);

    List<CompanyResponse> getAllCompanies();

    List<CompanyResponse> getActiveCompanies();

    List<CompanyResponse> getByEnterpriseType(Integer enterpriseTypeId);

    void delete(Long companyId);        // soft delete

    // Used by other services
    ManufactureCompany findActive(Long companyId);
}