package com.home.citylist.services;

import com.home.citylist.models.City;
import com.home.citylist.repositories.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<City> getAll(Integer page, Integer size) {
        return cityRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    public Optional<City> getById(long id) {
        return cityRepository.findById(id);
    }

    public Optional<City> getByName(String name) {
        return cityRepository.findCityByName(name);
    }
}
