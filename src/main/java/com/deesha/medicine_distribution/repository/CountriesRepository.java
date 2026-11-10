package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.Countries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountriesRepository extends JpaRepository<Countries,Long> {
}
