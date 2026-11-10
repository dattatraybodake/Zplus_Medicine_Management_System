package com.deesha.medicine_distribution.service;

import java.util.List;
import com.deesha.medicine_distribution.dto.LocationDtos.*;

public interface StateService {

    public StateResponse create(StateRequest r);

    public StateResponse get(Integer id);

    public List<StateResponse> list(Integer countryId);

    public StateResponse update(Integer id, StateRequest r);

    public void delete(Integer id);
}
