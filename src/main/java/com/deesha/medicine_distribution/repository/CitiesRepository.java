package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitiesRepository extends JpaRepository<Cities, Integer> {

    List<Cities> findByDistrictDistrictIdOrderByCityName(Integer districtId);

}
