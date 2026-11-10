package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.States;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatesRepository extends JpaRepository<States, Long> {
    List<States> findByCountryCountryIdOrderByStateName(Integer countryId);
}
