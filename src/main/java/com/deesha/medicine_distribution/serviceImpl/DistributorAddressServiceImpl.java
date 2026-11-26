package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.model.Distributor;
import com.deesha.medicine_distribution.model.DistributorAddress;
import com.deesha.medicine_distribution.repository.DistributorAddressRepository;
import com.deesha.medicine_distribution.repository.DistributorRepository;
import com.deesha.medicine_distribution.service.DistributorAddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DistributorAddressServiceImpl implements DistributorAddressService {

    private final DistributorAddressRepository distributorAddressRepository;
    private final DistributorRepository distributorRepository;

    @Override
    public DistributorAddress createDistributorAddress(DistributorAddress distributorAddress) {

        Long distributorId = distributorAddress.getDistributor().getDistributorId();
        Distributor distributor = distributorRepository.findById(distributorId).orElseThrow(() -> new EntityNotFoundException(
                        "Distributor not found with ID: " + distributorId));

        distributorAddress.setDistributor(distributor);
        return distributorAddressRepository.save(distributorAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public DistributorAddress getDistributorAddressById(Long id) {
        return distributorAddressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Distributor address not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DistributorAddress> getAllDistributorAddresses() {
        return distributorAddressRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DistributorAddress> getAddressesByDistributorId(Long distributorId) {
        return distributorAddressRepository.findByDistributor_DistributorId(distributorId);
    }

    @Override
    public DistributorAddress updateDistributorAddress(
            Long id,
            DistributorAddress updatedAddress) {

        DistributorAddress existingAddress = distributorAddressRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Distributor address not found with ID: " + id));

        existingAddress.setAddressLine1(updatedAddress.getAddressLine1());
        existingAddress.setAddressLine2(updatedAddress.getAddressLine2());
        existingAddress.setCity(updatedAddress.getCity());

        if (updatedAddress.getDistributor() != null) {
            Long distributorId = updatedAddress.getDistributor().getDistributorId();
            Distributor distributor = distributorRepository.findById(distributorId).orElseThrow(() -> new EntityNotFoundException("Distributor not found with ID: " + distributorId));
            existingAddress.setDistributor(distributor);
        }
        return distributorAddressRepository.save(existingAddress);
    }

    @Override
    public void deleteDistributorAddress(Long id) {

        DistributorAddress existingAddress = distributorAddressRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Distributor address not found with ID: " + id));
        distributorAddressRepository.delete(existingAddress);
    }
}