package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import com.deesha.medicine_distribution.model.*;
import com.deesha.medicine_distribution.repository.CitiesRepository;
//import com.deesha.medicine_distribution.repository.CityRepository;
import com.deesha.medicine_distribution.repository.CompanyPersonRepository;
import com.deesha.medicine_distribution.repository.DepartmentRepository;
import com.deesha.medicine_distribution.service.CompanyPersonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyPersonServiceImpl implements CompanyPersonService {

    private final CompanyPersonRepository repository;
    private final DepartmentRepository departmentRepository;
    private final CitiesRepository cityRepository;

    @Override
    @Transactional
    public CompanyPersonResponse create(CompanyPersonRequest request) {
        if (request.email() != null && !request.email().isBlank()
                && repository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException(
                    "Email already in use: " + request.email());
        }

        CompanyPerson entity = new CompanyPerson();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public CompanyPersonResponse update(Long id, CompanyPersonRequest request) {
        CompanyPerson entity = repository.findByPersonIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Company person not found with id: " + id));

        if (request.email() != null && !request.email().isBlank()) {
            repository.findByEmailIgnoreCase(request.email())
                    .filter(other -> !other.getPersonId().equals(id))
                    .ifPresent(other -> {
                        throw new IllegalArgumentException(
                                "Email already in use: " + request.email());
                    });
        }

        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyPersonResponse getById(Long id) {
        CompanyPerson entity = repository.findByIdWithDepartment(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Company person not found with id: " + id));
        return toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyPersonResponse> getAll() {
        return repository.findAllWithDepartment().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyPersonResponse> getByDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException(
                    "CompanyDepartment not found with id: " + departmentId);
        }
        return repository.findByDepartment_DepartmentIdAndIsDeletedFalse(departmentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CompanyPerson entity = repository.findByPersonIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Company person not found with id: " + id));
        entity.setIsDeleted(true);
        repository.save(entity);
    }

    private void apply(CompanyPerson entity, CompanyPersonRequest request) {
        entity.setFullName(request.fullName().trim());
        entity.setEmail(request.email());
        entity.setPhoneNumber(request.phoneNumber());
        entity.setDesignation(request.designation());
        entity.setDateOfJoining(request.dateOfJoining());
        entity.setIsActive(request.isActive() == null ? Boolean.TRUE : request.isActive());
        entity.setCreatedBy(request.createdBy());
        entity.setModifiedBy(request.createdBy());

        // ✅ Resolve city
        if (request.cityId() != null) {
            Cities city = cityRepository.findById(request.cityId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "City not found with id: " + request.cityId()));
            entity.setCity(city);
        } else {
            entity.setCity(null);
        }

        // Resolve department
        if (request.departmentId() != null) {
            CompanyDepartment department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "CompanyDepartment not found with id: " + request.departmentId()));
            entity.setDepartment(department);
        } else {
            entity.setDepartment(null);
        }
    }

    private CompanyPersonResponse toResponse(CompanyPerson e) {
        // Safe nested extraction
        var city = e.getCity();
        var district = city != null ? city.getDistrict() : null;
        var state = district != null ? district.getState() : null;
        var country = state != null ? state.getCountry() : null;

        String fullAddress = buildFullAddress(city, district, state, country);

        return new CompanyPersonResponse(
                e.getPersonId(),
                e.getFullName(),
                e.getEmail(),
                e.getPhoneNumber(),
                e.getDesignation(),
                e.getDateOfJoining(),

                city != null ? city.getCitiesId() : null,
                city != null ? city.getCityName() : null,
                city != null ? city.getPostalcodes() : null,
                district != null ? district.getDistrictName() : null,
                state != null ? state.getStateName() : null,
                country != null ? country.getCountryName() : null,
                fullAddress,

                e.getIsActive(),
                e.getDepartment() != null ? e.getDepartment().getDepartmentId() : null,
                e.getDepartment() != null ? e.getDepartment().getDepartmentName() : null,
                e.getCreatedBy(),
                e.getCreatedAt(),
                e.getModifiedBy(),
                e.getModifiedAt()
        );
    }

    private String buildFullAddress(Cities city, Districts district,
                                    States state, Countries country) {
        StringBuilder sb = new StringBuilder();
        if (city != null && city.getCityName() != null) {
            sb.append(city.getCityName());
        }
        if (district != null && district.getDistrictName() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(district.getDistrictName());
        }
        if (state != null && state.getStateName() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(state.getStateName());
        }
        if (country != null && country.getCountryName() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(country.getCountryName());
        }
        if (city != null && city.getPostalcodes() > 0) {
            if (sb.length() > 0) sb.append(" - ");
            sb.append(city.getPostalcodes());
        }
        return sb.toString();
    }
}