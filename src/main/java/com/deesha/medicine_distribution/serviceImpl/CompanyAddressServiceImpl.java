package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.AddressRequest;
import com.deesha.medicine_distribution.dto.CompanyDto.AddressResponse;
import com.deesha.medicine_distribution.exception.ResourceNotFoundException;
import com.deesha.medicine_distribution.model.Cities;
import com.deesha.medicine_distribution.model.CompanyAddress;
import com.deesha.medicine_distribution.model.ManufactureCompany;
import com.deesha.medicine_distribution.repository.CitiesRepository;
import com.deesha.medicine_distribution.repository.CompanyAddressRepository;
import com.deesha.medicine_distribution.service.CompaniesService;
import com.deesha.medicine_distribution.service.CompanyAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyAddressServiceImpl implements CompanyAddressService {

    private final CompanyAddressRepository companyAddressRepository;
    private final CompaniesService companiesService;
    private final CitiesRepository citiesRepository;

    public CompanyAddressServiceImpl(
            CompanyAddressRepository companyAddressRepository,
            CompaniesService companiesService,
            CitiesRepository citiesRepository) {

        this.companyAddressRepository = companyAddressRepository;
        this.companiesService = companiesService;
        this.citiesRepository = citiesRepository;
    }

    // ---------- CREATE ----------
    @Override
    public AddressResponse create(Long companyId, AddressRequest request) {

        ManufactureCompany company = companiesService.findActive(companyId);

        Cities city = citiesRepository.findById(request.citiesId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "City not found: " + request.citiesId()));

        if (companyAddressRepository.existsByCompany_CompanyIdAndAddressType(
                companyId, request.addressType())) {
            throw new IllegalArgumentException(
                    "An address of type " + request.addressType()
                            + " already exists for company " + companyId);
        }

        CompanyAddress address = new CompanyAddress();
        address.setCompany(company);
        apply(address, request, city);

        return toResponse(companyAddressRepository.save(address));
    }

    // ---------- READ ALL ----------
    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAll(Long companyId) {
        // Ensures company exists & is active before listing
        companiesService.findActive(companyId);

        return companyAddressRepository.findByCompany_CompanyId(companyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ---------- READ ONE ----------
    @Override
    @Transactional(readOnly = true)
    public AddressResponse getById(Long companyId, Long addressId) {
        CompanyAddress address = companyAddressRepository
                .findByAddressIdAndCompany_CompanyId(addressId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address " + addressId + " not found for company " + companyId));

        return toResponse(address);
    }

    // ---------- UPDATE ----------
    @Override
    public AddressResponse update(Long companyId, Long addressId, AddressRequest request) {

        CompanyAddress address = companyAddressRepository
                .findByAddressIdAndCompany_CompanyId(addressId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address " + addressId + " not found for company " + companyId));

        Cities city = citiesRepository.findById(request.citiesId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "City not found: " + request.citiesId()));

        // If address type changed, ensure no duplicate
        if (address.getAddressType() != request.addressType()
                && companyAddressRepository.existsByCompany_CompanyIdAndAddressTypeAndAddressIdNot(
                companyId, request.addressType(), addressId)) {
            throw new IllegalArgumentException(
                    "An address of type " + request.addressType()
                            + " already exists for company " + companyId);
        }

        apply(address, request, city);

        return toResponse(companyAddressRepository.save(address));
    }

    // ---------- DELETE ----------
    @Override
    public void delete(Long companyId, Long addressId) {
        CompanyAddress address = companyAddressRepository
                .findByAddressIdAndCompany_CompanyId(addressId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address " + addressId + " not found for company " + companyId));

        companyAddressRepository.delete(address);
    }

    // ---------- Helpers ----------
    private void apply(CompanyAddress address, AddressRequest request, Cities city) {
        address.setAddressType(request.addressType());
        address.setAddressLine1(request.addressLine1().trim());
        address.setAddressLine2(request.addressLine2());
        address.setCity(city);
    }

    private AddressResponse toResponse(CompanyAddress address) {
        Cities city = address.getCity();

        return new AddressResponse(
                address.getAddressId(),
                address.getCompany().getCompanyId(),
                address.getCompany().getCompanyName(),
                address.getAddressType(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                city.getCityName(),
                city.getDistrict().getDistrictName(),
                city.getDistrict().getState().getStateName(),
                city.getDistrict().getState().getCountry().getCountryName(),
                city.getPostalcodes()
        );
    }
}