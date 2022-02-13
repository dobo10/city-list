package com.home.citylist.repositories;

import com.home.citylist.models.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c")
    Optional<List<City>> findAllCities(Pageable pageable);

    @Query("select c from City c where c.name = :name")
    Optional<City> findCityByName(String name);
}
