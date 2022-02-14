package com.home.citylist.common;

import com.home.citylist.dto.CityDto;
import com.home.citylist.dto.CityUpdateDto;
import com.home.citylist.models.City;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;

public final class TestUtils {

    private TestUtils() {
        throw new AssertionError("This is an utility class, it shouldn't be instantiated.");
    }

    public static City constructCity() {
        return new City()
                .setId(1)
                .setName(RandomStringUtils.randomAlphabetic(15))
                .setPhoto(RandomStringUtils.randomAlphabetic(25));
    }

    public static CityDto constructCityDto() {
        return new CityDto(1,
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(20));
    }

    public static CityUpdateDto constructUpdateDto() {
        return new CityUpdateDto(1,
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(10));
    }
}
