package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import java.util.List;

public interface CompanyPersonService {

    CompanyPersonResponse create(CompanyPersonRequest request);

    CompanyPersonResponse update(Long id, CompanyPersonRequest request);

    CompanyPersonResponse getById(Long id);

    List<CompanyPersonResponse> getAll();

    List<CompanyPersonResponse> getByDepartment(Long departmentId);

    void delete(Long id);
}