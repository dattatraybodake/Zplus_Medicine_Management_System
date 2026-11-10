package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import com.deesha.medicine_distribution.model.CompanyPerson;
import com.deesha.medicine_distribution.model.CompanyDepartment;
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
        entity.setAddress(request.address());
        entity.setIsActive(request.isActive() == null ? Boolean.TRUE : request.isActive());
        entity.setCreatedBy(request.createdBy());
        entity.setModifiedBy(request.createdBy());

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
        return new CompanyPersonResponse(
                e.getPersonId(),
                e.getFullName(),
                e.getEmail(),
                e.getPhoneNumber(),
                e.getDesignation(),
                e.getDateOfJoining(),
                e.getAddress(),
                e.getIsActive(),
                e.getDepartment() != null ? e.getDepartment().getDepartmentId() : null,
                e.getDepartment() != null ? e.getDepartment().getDepartmentName() : null,
                e.getCreatedBy(),
                e.getCreatedAt(),
                e.getModifiedBy(),
                e.getModifiedAt()
        );
    }
}