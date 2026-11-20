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

    // Fetch department + full city hierarchy
    @Query("""
           SELECT p FROM CompanyPerson p
           LEFT JOIN FETCH p.department
           LEFT JOIN FETCH p.city c
           LEFT JOIN FETCH c.district d
           LEFT JOIN FETCH d.state s
           LEFT JOIN FETCH s.country
           WHERE p.isDeleted = false
           """)
    List<CompanyPerson> findAllWithDepartment();

    @Query("""
           SELECT p FROM CompanyPerson p
           LEFT JOIN FETCH p.department
           LEFT JOIN FETCH p.city c
           LEFT JOIN FETCH c.district d
           LEFT JOIN FETCH d.state s
           LEFT JOIN FETCH s.country
           WHERE p.personId = :id AND p.isDeleted = false
           """)
    Optional<CompanyPerson> findByIdWithDepartment(@Param("id") Long id);

    @Query("""
           SELECT p FROM CompanyPerson p
           LEFT JOIN FETCH p.department
           LEFT JOIN FETCH p.city c
           LEFT JOIN FETCH c.district d
           LEFT JOIN FETCH d.state s
           LEFT JOIN FETCH s.country
           WHERE p.department.departmentId = :departmentId AND p.isDeleted = false
           """)
    List<CompanyPerson> findByDepartment_DepartmentIdAndIsDeletedFalse(
            @Param("departmentId") Long departmentId);
}