package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.CompanyPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyPersonRepository extends JpaRepository<CompanyPerson, Long> {

    Optional<CompanyPerson> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<CompanyPerson> findAllByIsDeletedFalse();

    Optional<CompanyPerson> findByPersonIdAndIsDeletedFalse(Long personId);

    @Query("SELECT p FROM CompanyPerson p LEFT JOIN FETCH p.department WHERE p.isDeleted = false")
    List<CompanyPerson> findAllWithDepartment();

    @Query("SELECT p FROM CompanyPerson p LEFT JOIN FETCH p.department WHERE p.personId = :id AND p.isDeleted = false")
    Optional<CompanyPerson> findByIdWithDepartment(@Param("id") Long id);

    List<CompanyPerson> findByDepartment_DepartmentIdAndIsDeletedFalse(Long departmentId);
}