package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import com.deesha.medicine_distribution.model.Countries;
import com.deesha.medicine_distribution.repository.CountriesRepository;
import com.deesha.medicine_distribution.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountriesRepository repo;

    @Transactional
    public CountryResponse create(CountryRequest r) {
        if (r.countryId() == null) throw new IllegalArgumentException("countryId is required");
        if (repo.existsById(Long.valueOf(r.countryId()))) throw new IllegalStateException("Country already exists: " + r.countryId());
        Countries c = new Countries();
        c.setCountryId(r.countryId());
        c.setCountryName(r.countryName().trim());
        c.setIsoCode(r.isoCode());
        return CountryResponse.from(repo.saveAndFlush(c));
    }

    @Transactional(readOnly = true)
    public List<CountryResponse> list() {
        return repo.findAll().stream().map(CountryResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CountryResponse get(Integer id) { return CountryResponse.from(find(id)); }

    @Transactional
    public CountryResponse update(Integer id, CountryRequest r) {
        if (r.countryId() != null && !r.countryId().equals(id))
            throw new IllegalArgumentException("countryId in body does not match URL");
        Countries c = find(id);
        c.setCountryName(r.countryName().trim());
        c.setIsoCode(r.isoCode());
        return CountryResponse.from(repo.saveAndFlush(c));
    }

    @Transactional
    public void delete(Integer id) {
        repo.delete(find(id));
        repo.flush();   // surface FK violations now (409) instead of at commit
    }

    private Countries find(Integer countryId) {
        return repo.findById(Long.valueOf(countryId)).orElseThrow(() -> new EntityNotFoundException("Country not found: " + countryId));
    }
}

