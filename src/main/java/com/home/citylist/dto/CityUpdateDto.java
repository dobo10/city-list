package com.home.citylist.dto;

import javax.validation.constraints.NotEmpty;

public record CityUpdateDto(long id, @NotEmpty String name, @NotEmpty String photo) {
}
