package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.CompanyDepartment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<CompanyDepartment, Long> {

    Optional<CompanyDepartment> findByDepartmentNameIgnoreCase(String departmentName);

    Optional<CompanyDepartment> findByDepartmentCodeIgnoreCase(String departmentCode);

    boolean existsByDepartmentNameIgnoreCase(String departmentName);

    boolean existsByDepartmentCodeIgnoreCase(String departmentCode);

    List<CompanyDepartment> findAllByIsDeletedFalse();

    Optional<CompanyDepartment> findByDepartmentIdAndIsDeletedFalse(Long departmentId);
}