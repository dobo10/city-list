package com.home.citylist.repositories;

import com.home.citylist.models.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Page<City> findAll(Pageable pageable);

    Optional<City> findCityByName(String name);
}
