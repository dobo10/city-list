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
                .setName("Tokyo")
                .setPhoto("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg");
    }

    public static CityDto constructCityDto() {
        return new CityDto(2,
                "Jakarta",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Jakarta_Pictures-1.jpg/327px-Jakarta_Pictures-1.jpg");
    }

    public static CityUpdateDto constructUpdateDto() {
        return new CityUpdateDto(3,
                "Bangkok",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Bangkok_Montage_2021.jpg/375px-Bangkok_Montage_2021.jpg");
    }
}
