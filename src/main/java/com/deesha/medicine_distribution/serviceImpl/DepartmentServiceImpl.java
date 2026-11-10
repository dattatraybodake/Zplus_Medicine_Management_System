package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.CompanyDto.*;
import com.deesha.medicine_distribution.model.CompanyDepartment;
import com.deesha.medicine_distribution.repository.DepartmentRepository;
import com.deesha.medicine_distribution.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    @Override
    @Transactional
    public DepartmentResponse create(DepartmentRequest request) {
        if (repository.existsByDepartmentNameIgnoreCase(request.departmentName())) {
            throw new IllegalArgumentException(
                    "CompanyDepartment already exists: " + request.departmentName());
        }
        if (request.departmentCode() != null
                && repository.existsByDepartmentCodeIgnoreCase(request.departmentCode())) {
            throw new IllegalArgumentException(
                    "CompanyDepartment code already in use: " + request.departmentCode());
        }

        CompanyDepartment entity = new CompanyDepartment();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        CompanyDepartment entity = repository.findByDepartmentIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "CompanyDepartment not found with id: " + id));

        repository.findByDepartmentNameIgnoreCase(request.departmentName())
                .filter(other -> !other.getDepartmentId().equals(id))
                .ifPresent(other -> {
                    throw new IllegalArgumentException(
                            "CompanyDepartment name already in use: " + request.departmentName());
                });

        if (request.departmentCode() != null) {
            repository.findByDepartmentCodeIgnoreCase(request.departmentCode())
                    .filter(other -> !other.getDepartmentId().equals(id))
                    .ifPresent(other -> {
                        throw new IllegalArgumentException(
                                "CompanyDepartment code already in use: " + request.departmentCode());
                    });
        }

        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(Long id) {
        CompanyDepartment entity = repository.findByDepartmentIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "CompanyDepartment not found with id: " + id));
        return toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAll() {
        return repository.findAllByIsDeletedFalse().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CompanyDepartment entity = repository.findByDepartmentIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "CompanyDepartment not found with id: " + id));
        entity.setIsDeleted(true);
        repository.save(entity);
    }

    private void apply(CompanyDepartment entity, DepartmentRequest request) {
        entity.setDepartmentName(request.departmentName().trim());
        entity.setDepartmentCode(request.departmentCode());
        entity.setDescription(request.description());
        entity.setHeadOfDepartment(request.headOfDepartment());
        entity.setIsActive(request.isActive() == null ? Boolean.TRUE : request.isActive());
        entity.setCreatedBy(request.createdBy());
        entity.setModifiedBy(request.createdBy());
    }

    private DepartmentResponse toResponse(CompanyDepartment e) {
        return new DepartmentResponse(
                e.getDepartmentId(),
                e.getDepartmentName(),
                e.getDepartmentCode(),
                e.getDescription(),
                e.getHeadOfDepartment(),
                e.getIsActive(),
                e.getCreatedBy(),
                e.getCreatedAt(),
                e.getModifiedBy(),
                e.getModifiedAt()
        );
    }
}