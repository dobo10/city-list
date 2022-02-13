package com.home.citylist.mappers;

import com.home.citylist.dto.CityDto;
import com.home.citylist.dto.CityUpdateDto;
import com.home.citylist.models.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityMapper {

    @Mapping(source = "photo", target = "imageUrl")
    CityDto mapToCityDto(City city);

    List<CityDto> mapToCityDtos(List<City> city);

    void update(CityUpdateDto updateDto, @MappingTarget City city);
}
