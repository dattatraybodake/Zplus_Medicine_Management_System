package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.exception.DuplicateResourceException;
import com.deesha.medicine_distribution.exception.ResourceNotFoundException;
import com.deesha.medicine_distribution.model.Distributor;
import com.deesha.medicine_distribution.repository.DistributorRepository;
import com.deesha.medicine_distribution.service.DistributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class DistributorServiceImpl implements DistributorService {

    private final DistributorRepository repository;

    @Override
    public Distributor create(Distributor d) {
        if (repository.existsByDistributorEmailIgnoreCaseAndDeletedAtIsNull(d.getDistributorEmail())) {
            throw new DuplicateResourceException("Distributor email already exists: " + d.getDistributorEmail());
        }
        d.setDistributorId(null);
        d.setVersion(null);
        d.setCreatedAt(null);
        d.setModifiedAt(null);
        d.setDeletedAt(null);
        d.setDeletedBy(null);
        d.setActive(true);
        if (d.getStartDate() == null) {
            d.setStartDate(LocalDateTime.now());
        }
        return repository.save(d);
    }

    @Override
    @Transactional(readOnly = true)
    public Distributor getById(Long id) {
        return repository.findByDistributorIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Distributor> getAll(Distributor.Status status, String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return repository.findByDistributorNameContainingIgnoreCaseAndDeletedAtIsNull(name.trim(), pageable);
        }
        if (status != null) {
            return repository.findAllByStatusAndDeletedAtIsNull(status, pageable);
        }
        return repository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public Distributor update(Long id, Distributor in) {
        Distributor existing = getById(id);

        // Optimistic-lock check: reject stale client copies
        if (in.getVersion() != null && !in.getVersion().equals(existing.getVersion())) {
            throw new ObjectOptimisticLockingFailureException(Distributor.class, id);
        }

        if (repository.existsByDistributorEmailIgnoreCaseAndDistributorIdNotAndDeletedAtIsNull(
                in.getDistributorEmail(), id)) {
            throw new DuplicateResourceException("Distributor email already exists: " + in.getDistributorEmail());
        }

        existing.setDistributorName(in.getDistributorName());
        existing.setTradeName(in.getTradeName());
        existing.setBusinessType(in.getBusinessType());
        existing.setDistributorEmail(in.getDistributorEmail());
        existing.setPhone(in.getPhone());
        if (in.getStartDate() != null) {
            existing.setStartDate(in.getStartDate());
        }
        existing.setStatus(in.getStatus());
        existing.setRemarks(in.getRemarks());
        existing.setActive(in.isActive());
        existing.setModifiedBy(in.getModifiedBy());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String deletedBy) {
        Distributor existing = getById(id);
        existing.setActive(false);
        existing.setDeletedAt(LocalDateTime.now());
        existing.setDeletedBy(deletedBy);
        repository.save(existing);
    }
}