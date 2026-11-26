package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.model.DistributorAddress;
import java.util.List;

public interface DistributorAddressService {

    DistributorAddress createDistributorAddress(DistributorAddress distributorAddress);

    DistributorAddress getDistributorAddressById(Long id);

    List<DistributorAddress> getAllDistributorAddresses();

    List<DistributorAddress> getAddressesByDistributorId(Long distributorId);

    DistributorAddress updateDistributorAddress(Long id, DistributorAddress distributorAddress);

    void deleteDistributorAddress(Long id);
}