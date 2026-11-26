package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.CompanyAddress;
import com.deesha.medicine_distribution.model.DistributorAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DistributorAddressRepository extends JpaRepository<DistributorAddress,Long> {
    List<DistributorAddress> findByDistributor_DistributorId(Long distributorId);

}
