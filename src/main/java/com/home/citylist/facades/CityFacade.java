package com.home.citylist.facades;

import com.home.citylist.dto.CityDto;
import com.home.citylist.dto.CityUpdateDto;
import com.home.citylist.mappers.CityMapper;
import com.home.citylist.models.City;
import com.home.citylist.services.CityService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class CityFacade {

    private final CityService cityService;

    private final CityMapper cityMapper;

    public CityFacade(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    public ResponseEntity getAll(Integer page, Integer size) {
        Page<City> cities = cityService.getAll(page, size);
        Page<CityDto> cityDtos = cities.map(cityMapper::mapToCityDto);
        return ResponseEntity.ok(cityDtos);
    }

    public Optional<CityDto> getCity(long id) {
        return cityService.getById(id).map(cityMapper::mapToCityDto);
    }

    public Optional<CityDto> getByName(String name) {
        return cityService.getByName(name).map(cityMapper::mapToCityDto);
    }

    public Optional<CityDto> update(CityUpdateDto updateDto) {
        return cityService.getById(updateDto.id()).map(city -> {
            cityMapper.update(updateDto, city);
            return cityMapper.mapToCityDto(city);
        });
    }
}
