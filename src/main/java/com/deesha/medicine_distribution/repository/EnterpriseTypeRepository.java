package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.EnterpriseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseTypeRepository extends JpaRepository<EnterpriseType, Integer> {
}
