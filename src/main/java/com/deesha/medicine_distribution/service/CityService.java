package com.deesha.medicine_distribution.service;

import jakarta.validation.Valid;
import com.deesha.medicine_distribution.dto.LocationDtos.*;
import java.util.List;

public interface CityService {
    public CityResponse create(CityRequest r);
    public List<CityResponse> list(Integer districtId);
    public CityResponse get(Integer id);
    public void delete(Integer id);
    CityResponse update(Integer id, @Valid CityRequest req);
}