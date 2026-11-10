package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.CompanyDto.*;

import java.util.List;

public interface CompanyAddressService {
    AddressResponse create(Long companyId, AddressRequest request);

    List<AddressResponse> getAll(Long companyId);

    AddressResponse getById(Long companyId, Long addressId);

    AddressResponse update(Long companyId, Long addressId, AddressRequest request);

    void delete(Long companyId, Long addressId);
}