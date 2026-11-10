package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.LocationDtos.*;
import java.util.List;

public interface CountryService {

    public CountryResponse create(CountryRequest r);

    public List<CountryResponse> list();

    public CountryResponse get(Integer id);

    public CountryResponse update(Integer id, CountryRequest r);

    public void delete(Integer id);
}
