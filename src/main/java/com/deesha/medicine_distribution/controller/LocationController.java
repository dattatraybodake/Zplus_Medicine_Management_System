package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.dto.LookupDto;
import com.deesha.medicine_distribution.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/** Cascading dropdowns: country -> state -> district -> city. */
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final CountriesRepository countries;
    private final StatesRepository states;
    private final DistrictsRepository districts;
    private final CitiesRepository cities;

    @GetMapping("/countries")
    public List<LookupDto> countries()
    {
        return countries.findAll().stream().map(c -> new LookupDto(String.valueOf(c.getCountryId()), c.getCountryName())).toList();
    }

    @GetMapping("/countries/{countryId}/states")
    public List<LookupDto> states(@PathVariable Integer countryId) {
        return states.findByCountryCountryIdOrderByStateName(countryId).stream()
                .map(s -> new LookupDto(String.valueOf(s.getStateId()), s.getStateName())).toList();
    }

    @GetMapping("/states/{stateId}/districts")
    public List<LookupDto> districts(@PathVariable Integer stateId) {
        return districts.findByStateStateIdOrderByDistrictName(stateId).stream()
                .map(d -> new LookupDto(String.valueOf(d.getDistrictId()), d.getDistrictName())).toList();
    }

//    @GetMapping("/districts/{districtId}/cities")
//    public List<LookupDto> cities(@PathVariable Integer districtId) {
//        return cities.findByDistrictDistrictIdOrderByCityName(districtId).stream()
//                .map(c -> new LookupDto(c.getCitiesId(), c.getCityName())).toList();
//    }
}
