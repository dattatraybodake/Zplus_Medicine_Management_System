package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import com.deesha.medicine_distribution.model.Cities;
import com.deesha.medicine_distribution.model.Districts;
import com.deesha.medicine_distribution.repository.CitiesRepository;
import com.deesha.medicine_distribution.repository.DistrictsRepository;
import com.deesha.medicine_distribution.service.CityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CitiesRepository repo;
    private final DistrictsRepository districts;

    @Transactional
    public CityResponse create(CityRequest r) {

        if (r.citiesId() == null) throw new IllegalArgumentException("citiesId is required");

        if (repo.existsById(r.citiesId())) throw new IllegalStateException("City already exists: " + r.citiesId());

        Cities c = new Cities();
        c.setCitiesId(r.citiesId());

        apply(c, r);

        return CityResponse.from(repo.saveAndFlush(c));
    }

    /** districtId is an optional filter. */
    @Transactional(readOnly = true)
    public List<CityResponse> list(Integer districtId) {
        List<Cities> rows = districtId == null
                ? repo.findAll() : repo.findByDistrictDistrictIdOrderByCityName(districtId);
        return rows.stream().map(CityResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CityResponse get(Integer id) { return CityResponse.from(find(id)); }

    @Transactional
    public CityResponse update(Integer id, CityRequest r) {
        if (r.citiesId() != null && !r.citiesId().equals(id))
            throw new IllegalArgumentException("citiesId in body does not match URL");
        Cities c = find(id);
        apply(c, r);
        return CityResponse.from(repo.saveAndFlush(c));
    }

    @Transactional
    public void delete(Integer id) {
        repo.delete(find(id));
        repo.flush();
    }

    private void apply(Cities c, CityRequest r) {
        Districts d = districts.findById(Long.valueOf(r.districtId())).orElseThrow(() -> new EntityNotFoundException("District not found: " + r.districtId()));
        c.setCityName(r.cityName().trim());
        c.setDistrict(d);
    }

    private Cities find(Integer id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found: " + id));
    }
}
