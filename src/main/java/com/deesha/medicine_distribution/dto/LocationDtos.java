package com.deesha.medicine_distribution.dto;

import com.deesha.medicine_distribution.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Request/response records for country, state, district and city CRUD. */
public final class LocationDtos {
    private LocationDtos() {}

    // ids are supplied by the client (the entities have no @GeneratedValue); required on create
    public record CountryRequest(Integer countryId,
                                 @NotBlank @Size(max = 100) String countryName,
                                 @Size(max = 10) String isoCode) {}
    public record CountryResponse(Integer countryId, String countryName, String isoCode) {
        public static CountryResponse from(Countries c) {
            return new CountryResponse(c.getCountryId(), c.getCountryName(), c.getIsoCode());
        }
    }

    public record StateRequest(Integer stateId,
                               @NotBlank @Size(max = 100) String stateName,
                               @NotNull Integer countryId) {}
    public record StateResponse(Integer stateId, String stateName, Integer countryId, String countryName) {
        public static StateResponse from(States s) {
            return new StateResponse(s.getStateId(), s.getStateName(),
                    s.getCountry().getCountryId(), s.getCountry().getCountryName());
        }
    }

    public record DistrictRequest(Integer districtId,
                                  @NotBlank @Size(max = 100) String districtName,
                                  @NotNull Integer stateId) {}
    public record DistrictResponse(Integer districtId, String districtName, Integer stateId, String stateName) {
        public static DistrictResponse from(Districts d) {
            return new DistrictResponse(d.getDistrictId(), d.getDistrictName(),
                    d.getState().getStateId(), d.getState().getStateName());
        }
    }

    public record CityRequest(Integer citiesId, @NotBlank @Size(max = 100) String cityName, @NotNull Integer districtId) {}
    public record CityResponse(Integer citiesId, String cityName, Integer districtId, String districtName, Integer stateId, String stateName) {
        public static CityResponse from(Cities c) {
            Districts d = c.getDistrict();
            return new CityResponse(c.getCitiesId(), c.getCityName(),
                    d.getDistrictId(), d.getDistrictName(),
                    d.getState().getStateId(), d.getState().getStateName());
        }
    }
}
