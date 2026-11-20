package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CitiesRepository extends JpaRepository<Cities, Integer> {

    List<Cities> findByDistrictDistrictIdOrderByCityName(Integer districtId);

    @Query("""
           SELECT c FROM Cities c
           LEFT JOIN FETCH c.district d
           LEFT JOIN FETCH d.state s
           LEFT JOIN FETCH s.country
           WHERE c.citiesId = :id
           """)
    Optional<Cities> findByIdWithHierarchy(@Param("id") Integer id);
}
