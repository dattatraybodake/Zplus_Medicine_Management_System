package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.Districts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictsRepository extends JpaRepository<Districts, Long> {
    List<Districts> findByStateStateIdOrderByDistrictName(Integer stateId);
}
