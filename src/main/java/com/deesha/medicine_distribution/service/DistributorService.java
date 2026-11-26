package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.model.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DistributorService {

    Distributor create(Distributor distributor);
    Distributor getById(Long id);
    Page<Distributor> getAll(Distributor.Status status, String name, Pageable pageable);
    Distributor update(Long id, Distributor distributor);
    void delete(Long id, String deletedBy);
}