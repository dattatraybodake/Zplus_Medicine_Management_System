package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import com.deesha.medicine_distribution.model.Districts;
import com.deesha.medicine_distribution.model.States;
import com.deesha.medicine_distribution.repository.DistrictsRepository;
import com.deesha.medicine_distribution.repository.StatesRepository;
import com.deesha.medicine_distribution.service.DistrictService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictsRepository repo;
    private final StatesRepository states;

    @Transactional
    public DistrictResponse create(DistrictRequest r) {
        if (r.districtId() == null) throw new IllegalArgumentException("districtId is required");
        if (repo.existsById(Long.valueOf(r.districtId()))) throw new IllegalStateException("District already exists: " + r.districtId());
        Districts d = new Districts();
        d.setDistrictId(r.districtId());
        apply(d, r);
        return DistrictResponse.from(repo.saveAndFlush(d));
    }

    /** stateId is an optional filter. */
    @Transactional(readOnly = true)
    public List<DistrictResponse> list(Integer stateId) {
        List<Districts> rows = stateId == null
                ? repo.findAll() : repo.findByStateStateIdOrderByDistrictName(stateId);
        return rows.stream().map(DistrictResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DistrictResponse get(Integer id) { return DistrictResponse.from(find(id)); }

    @Transactional
    public DistrictResponse update(Integer id, DistrictRequest r) {
        if (r.districtId() != null && !r.districtId().equals(id))
            throw new IllegalArgumentException("districtId in body does not match URL");
        Districts d = find(id);
        apply(d, r);
        return DistrictResponse.from(repo.saveAndFlush(d));
    }

    @Transactional
    public void delete(Integer id) {
        repo.delete(find(id));
        repo.flush();
    }

    private void apply(Districts d, DistrictRequest r) {
        States s = states.findById(Long.valueOf(r.stateId()))
                .orElseThrow(() -> new EntityNotFoundException("State not found: " + r.stateId()));
        d.setDistrictName(r.districtName().trim());
        d.setState(s);
    }

    private Districts find(Integer id) {
        return repo.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException("District not found: " + id));
    }
}
