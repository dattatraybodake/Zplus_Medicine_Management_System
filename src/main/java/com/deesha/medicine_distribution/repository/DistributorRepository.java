package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {

    Optional<Distributor> findByDistributorIdAndDeletedAtIsNull(Long distributorId);

    Page<Distributor> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Distributor> findAllByStatusAndDeletedAtIsNull(Distributor.Status status, Pageable pageable);

    Page<Distributor> findByDistributorNameContainingIgnoreCaseAndDeletedAtIsNull(String name, Pageable pageable);

    boolean existsByDistributorEmailIgnoreCaseAndDeletedAtIsNull(String email);

    boolean existsByDistributorEmailIgnoreCaseAndDistributorIdNotAndDeletedAtIsNull(String email, Long id);
}