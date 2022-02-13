package com.home.citylist.services;

import com.home.citylist.dto.CityDto;
import com.home.citylist.dto.CityUpdateDto;
import com.home.citylist.mappers.CityMapper;
import com.home.citylist.models.City;
import com.home.citylist.repositories.CityRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public Optional<List<CityDto>> getAll(Integer page, Integer size) {
        Optional<List<City>> cities = cityRepository.findAllCities(PageRequest.of(page, size));
        if (cities.get().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cityMapper.mapToCityDtos(cities.get()));
    }

    public Optional<CityDto> getById(long id) {
        return cityRepository.findById(id).map(cityMapper::mapToCityDto);
    }

    public Optional<CityDto> getByName(String name) {
        Optional<City> city = cityRepository.findCityByName(name);
        return city.map(cityMapper::mapToCityDto);
    }

    public Optional<CityDto> update(CityUpdateDto updateDto) {
        return cityRepository.findById(updateDto.id()).map(city -> {
           cityMapper.update(updateDto, city);
           return cityMapper.mapToCityDto(city);
        });
    }
}