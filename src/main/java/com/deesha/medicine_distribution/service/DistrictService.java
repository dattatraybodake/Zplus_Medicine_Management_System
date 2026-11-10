package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import java.util.List;

public interface DistrictService {

    public DistrictResponse create(DistrictRequest r);

    public List<DistrictResponse> list(Integer stateId);

    public DistrictResponse get(Integer id);

    public DistrictResponse update(Integer id, DistrictRequest r);

    public void delete(Integer id);
}
