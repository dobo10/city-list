package com.home.citylist.services;

import com.home.citylist.dto.CityDto;
import com.home.citylist.mappers.CityMapper;
import com.home.citylist.models.City;
import com.home.citylist.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    private static final Long CITY_ID = 1L;
    private static final String CITY_NAME = "testCityName";
    private static final String CITY_URL = "testCityUrl";

    @InjectMocks
    private CityService serviceToTest;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityMapper cityMapper;

    @Test
    public void getAll_shouldReturnCity() {
        int offset = 0;
        int limit = 5;
        CityDto cityDto = constructCityDto();
        List<CityDto> cityDtos = Collections.singletonList(cityDto);
        City city = constructCity();
        List<City> cities = Collections.singletonList(city);
        when(cityRepository.findAllCities(PageRequest.of(offset, limit))).thenReturn(Optional.of(cities));
        when(cityMapper.mapToCityDtos(cities)).thenReturn(cityDtos);

        Optional<List<CityDto>> result = serviceToTest.getAll(offset, limit);

        assertTrue(result.isPresent());
        assertThat(result.get().get(0).id()).isEqualTo(CITY_ID);
        assertThat(result.get().get(0).name()).isEqualTo(CITY_NAME);
    }

    @Test
    public void getAll_whenNoCitiesFound_shouldReturnEmptyOptional() {
        int offset = 0;
        int limit = 5;
        when(cityRepository.findAllCities(PageRequest.of(offset, limit))).thenReturn(Optional.of(new ArrayList<>()));

        Optional<List<CityDto>> result = serviceToTest.getAll(offset, limit);

        assertFalse(result.isPresent());
    }

    @Test
    public void getById_shouldReturnCity() {
        CityDto cityDto = constructCityDto();
        City city = constructCity();
        when(cityRepository.findById(CITY_ID)).thenReturn(Optional.of(city));
        when(cityMapper.mapToCityDto(city)).thenReturn(cityDto);

        Optional<CityDto> result = serviceToTest.getById(CITY_ID);

        assertTrue(result.isPresent());
        assertThat(result.get().id()).isEqualTo(CITY_ID);
        assertThat(result.get().name()).isEqualTo(CITY_NAME);
    }

    @Test
    public void getById_whenCityNotFound_shouldReturnEmptyOptional() {
        when(cityRepository.findById(CITY_ID)).thenReturn(Optional.empty());

        Optional<CityDto> result = serviceToTest.getById(CITY_ID);

        assertFalse(result.isPresent());
    }

    @Test
    public void getByName_shouldReturnCity() {
        CityDto cityDto = constructCityDto();
        City city = constructCity();
        when(cityRepository.findCityByName(CITY_NAME)).thenReturn(Optional.of(city));
        when(cityMapper.mapToCityDto(city)).thenReturn(cityDto);

        Optional<CityDto> result = serviceToTest.getByName(CITY_NAME);

        assertTrue(result.isPresent());
        assertThat(result.get().id()).isEqualTo(CITY_ID);
        assertThat(result.get().name()).isEqualTo(CITY_NAME);
    }

    @Test
    public void getByName_whenCityNotFound_shouldReturnEmptyOptional() {
        when(cityRepository.findCityByName(CITY_NAME)).thenReturn(Optional.empty());

        Optional<CityDto> result = serviceToTest.getByName(CITY_NAME);

        assertFalse(result.isPresent());
    }

    private City constructCity() {
        return new City()
                .setId(CITY_ID)
                .setName(CITY_NAME)
                .setPhoto(CITY_URL);
    }

    private CityDto constructCityDto() {
        return new CityDto(CITY_ID, CITY_NAME, CITY_URL);
    }

}