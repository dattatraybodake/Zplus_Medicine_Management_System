package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import com.deesha.medicine_distribution.model.Countries;
import com.deesha.medicine_distribution.model.States;
import com.deesha.medicine_distribution.repository.CountriesRepository;
import com.deesha.medicine_distribution.repository.StatesRepository;
import com.deesha.medicine_distribution.service.StateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StatesRepository repo;
    private final CountriesRepository countries;

    @Override
    public StateResponse create(StateRequest r) {
        if (r.stateId() == null) throw new IllegalArgumentException("stateId is required");
        if (repo.existsById(Long.valueOf(r.stateId()))) throw new IllegalStateException("State already exists: " + r.stateId());
        States s = new States();
        s.setStateId(r.stateId());
        apply(s, r);
        return StateResponse.from(repo.saveAndFlush(s));
    }


    // countryId is an optional filter.
    @Transactional(readOnly = true)
    public List<StateResponse> list(Integer countryId) {
        List<States> rows = countryId == null ? repo.findAll() : repo.findByCountryCountryIdOrderByStateName(countryId);
        return rows.stream().map(StateResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public StateResponse get(Integer id) { return StateResponse.from(find(id)); }

    @Transactional
    public StateResponse update(Integer id, StateRequest r) {
        if (r.stateId() != null && !r.stateId().equals(id))
            throw new IllegalArgumentException("stateId in body does not match URL");
        States s = find(id);
        apply(s, r);
        return StateResponse.from(repo.saveAndFlush(s));
    }

    @Transactional
    public void delete(Integer id) {
        repo.delete(find(id));
        repo.flush();
    }

    private void apply(States s, StateRequest r) {
        Countries c = countries.findById(Long.valueOf(r.countryId())).orElseThrow(() -> new EntityNotFoundException("Country not found: " + r.countryId()));
        s.setStateName(r.stateName().trim());
        s.setCountry(c);
    }

    private States find(Integer id) {
        return repo.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException("State not found: " + id));
    }
}
