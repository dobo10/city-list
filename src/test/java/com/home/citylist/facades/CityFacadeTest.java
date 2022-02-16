package com.home.citylist.facades;

import com.home.citylist.dto.CityDto;
import com.home.citylist.dto.CityUpdateDto;
import com.home.citylist.mappers.CityMapper;
import com.home.citylist.mappers.CityMapperImpl;
import com.home.citylist.models.City;
import com.home.citylist.services.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.home.citylist.common.TestUtils.constructCity;
import static com.home.citylist.common.TestUtils.constructCityDto;
import static com.home.citylist.common.TestUtils.constructUpdateDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CityFacade.class, CityMapperImpl.class})
class CityFacadeTest {

    @Autowired
    private CityFacade cityFacade;

    @MockBean
    private CityService cityService;

    @MockBean
    private CityMapper cityMapper;

    @Test
    public void getCityById_shouldReturnCityFound() {
        City city = constructCity();
        CityDto cityDto = constructCityDto();
        when(cityService.getById(city.getId())).thenReturn(Optional.of(city));
        when(cityMapper.mapToCityDto(city)).thenReturn(cityDto);

        Optional<CityDto> cityFound = cityFacade.getCity(city.getId());

        assertTrue(cityFound.isPresent());
        assertThat(cityFound.get().id()).isEqualTo(cityDto.id());
        assertThat(cityFound.get().name()).isEqualTo(cityDto.name());
        assertThat(cityFound.get().imageUrl()).isEqualTo(cityDto.imageUrl());
    }

    @Test
    public void getCityByName_shouldReturnCityFound() {
        City city = constructCity();
        CityDto cityDto = constructCityDto();
        when(cityService.getByName(city.getName())).thenReturn(Optional.of(city));
        when(cityMapper.mapToCityDto(city)).thenReturn(cityDto);

        Optional<CityDto> cityFound = cityFacade.getByName(city.getName());

        assertTrue(cityFound.isPresent());
        assertThat(cityFound.get().id()).isEqualTo(cityDto.id());
        assertThat(cityFound.get().name()).isEqualTo(cityDto.name());
        assertThat(cityFound.get().imageUrl()).isEqualTo(cityDto.imageUrl());
    }

    @Test
    public void getAll_shouldReturnCityListFound() {
        int offset = 0;
        int limit = 3;
        City firstCity = constructCity();
        City secondCity = constructCity();
        List<City> cities = Arrays.asList(firstCity, secondCity);
        CityDto firstCityDto = constructCityDto();
        CityDto secondCityDto = constructCityDto();
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<City> pageResult = new PageImpl<>(cities, pageRequest, cities.size());
        when(cityService.getAll(offset, limit)).thenReturn(pageResult);
        when(cityMapper.mapToCityDto(firstCity)).thenReturn(firstCityDto);
        when(cityMapper.mapToCityDto(secondCity)).thenReturn(secondCityDto);

        ResponseEntity citiesFound = cityFacade.getAll(offset, limit);

        Page<CityDto> foundDtosList = (Page<CityDto>) citiesFound.getBody();
        assertThat(foundDtosList).isNotEmpty();
        assertThat(foundDtosList.getTotalElements()).isEqualTo(2);
        assertThat(foundDtosList.getContent().get(0).name()).isEqualTo(firstCityDto.name());
        assertThat(foundDtosList.getContent().get(0).imageUrl()).isEqualTo(firstCityDto.imageUrl());
        assertThat(foundDtosList.getContent().get(1).name()).isEqualTo(secondCityDto.name());
        assertThat(foundDtosList.getContent().get(1).imageUrl()).isEqualTo(secondCityDto.imageUrl());
    }

    @Test
    public void update_shouldReturnUpdatedCity() {
        City city = constructCity();
        CityDto cityDto = constructCityDto();
        CityUpdateDto updateDto = constructUpdateDto();
        when(cityService.getById(updateDto.id())).thenReturn(Optional.of(city));
        when(cityMapper.mapToCityDto(city)).thenReturn(cityDto);

        Optional<CityDto> updatedCity = cityFacade.update(updateDto);

        assertTrue(updatedCity.isPresent());
        assertThat(updatedCity.get().id()).isEqualTo(cityDto.id());
        assertThat(updatedCity.get().name()).isEqualTo(cityDto.name());
        assertThat(updatedCity.get().imageUrl()).isEqualTo(cityDto.imageUrl());
    }
}