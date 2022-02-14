package com.home.citylist.services;

import com.home.citylist.models.City;
import com.home.citylist.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.home.citylist.common.TestUtils.constructCity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @InjectMocks
    private CityService serviceToTest;

    @Mock
    private CityRepository cityRepository;

    @Test
    public void getAll_shouldReturnCity() {
        int offset = 0;
        int limit = 5;
        City city = constructCity();
        List<City> cities = Collections.singletonList(city);
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by("id"));
        Page<City> pageResult = new PageImpl<>(cities, pageRequest, cities.size());
        when(cityRepository.findAll(pageRequest)).thenReturn(pageResult);

        List<City> result = serviceToTest.getAll(offset, limit);

        assertThat(result.get(0).getId()).isEqualTo(city.getId());
        assertThat(result.get(0).getName()).isEqualTo(city.getName());
    }

    @Test
    public void getAll_whenNoCitiesFound_shouldReturnEmptyOptional() {
        int offset = 0;
        int limit = 5;
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by("id"));
        Page<City> pageResult = new PageImpl<>(new ArrayList<>(), pageRequest, 0);
        when(cityRepository.findAll(pageRequest)).thenReturn(pageResult);

        List<City> result = serviceToTest.getAll(offset, limit);

        assertThat(result.size()).isZero();
    }

    @Test
    public void getById_shouldReturnCity() {
        City city = constructCity();
        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));

        Optional<City> result = serviceToTest.getById(city.getId());

        assertTrue(result.isPresent());
        assertThat(result.get().getId()).isEqualTo(city.getId());
        assertThat(result.get().getName()).isEqualTo(city.getName());
    }

    @Test
    public void getById_whenCityNotFound_shouldReturnEmptyOptional() {
        long cityId = 5L;
        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        Optional<City> result = serviceToTest.getById(cityId);

        assertFalse(result.isPresent());
    }

    @Test
    public void getByName_shouldReturnCity() {
        City city = constructCity();
        when(cityRepository.findCityByName(city.getName())).thenReturn(Optional.of(city));

        Optional<City> result = serviceToTest.getByName(city.getName());

        assertTrue(result.isPresent());
        assertThat(result.get().getId()).isEqualTo(city.getId());
        assertThat(result.get().getName()).isEqualTo(city.getName());
    }

    @Test
    public void getByName_whenCityNotFound_shouldReturnEmptyOptional() {
        String testName = "testName";
        when(cityRepository.findCityByName(testName)).thenReturn(Optional.empty());

        Optional<City> result = serviceToTest.getByName(testName);

        assertFalse(result.isPresent());
    }
}