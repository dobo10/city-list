package com.home.citylist.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.citylist.dto.CityDto;
import com.home.citylist.dto.CityUpdateDto;
import com.home.citylist.services.CityService;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CitiesController.class)
class CitiesControllerTest {

    private static final Long CITY_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    @Test
    void getAllCities_shouldReturnStatusOk() throws Exception {
        CityDto firstCity = constructCityDto();
        CityDto secondCity = constructCityDto();
        List<CityDto> cityDtoList = Arrays.asList(firstCity, secondCity);
        when(cityService.getAll(0, 5)).thenReturn(Optional.of(cityDtoList));

        this.mockMvc.perform(get("/cities/v1")).andExpect(status().isOk());
    }

    @Test
    void getAllCities_whenNoCitiesFound_shouldReturnStatusNotFound() throws Exception {
        when(cityService.getAll(0, 5)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/cities/v1")).andExpect(status().isNotFound());
    }

    @Test
    void getCity_shouldReturnStatusOk() throws Exception {
        CityDto firstCity = constructCityDto();
        when(cityService.getById(CITY_ID)).thenReturn(Optional.of(firstCity));

        this.mockMvc.perform(get("/cities/v1/{id}", CITY_ID)).andExpect(status().isOk());
    }

    @Test
    void getCity_whenNotFoundById_shouldReturnStatusNotFound() throws Exception {
        when(cityService.getById(1)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/cities/v1/{id}", CITY_ID)).andExpect(status().isNotFound());
    }

    @Test
    void getByName_shouldReturnStatusOk() throws Exception {
        CityDto firstCity = constructCityDto();
        when(cityService.getByName(any())).thenReturn(Optional.of(firstCity));

        this.mockMvc.perform(get("/cities//v1/city?name=" + firstCity.name())).andExpect(status().isOk());
    }

    @Test
    void getByName_whenCityNotfound_shouldReturnStatusNotFound() throws Exception {
        when(cityService.getByName(any())).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/cities//v1/city?name=testCity")).andExpect(status().isNotFound());
    }

    @Test
    void update_shouldReturnStatusOk() throws Exception {
        CityDto firstCity = constructCityDto();
        CityUpdateDto updateDto = constructUpdateDto();
        String requestBody = objectMapper.writeValueAsString(updateDto);
        when(cityService.update(updateDto)).thenReturn(Optional.of(firstCity));

        this.mockMvc.perform(put("/cities/v1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void update_whenCityNotfound_shouldReturnStatusNotFound() throws Exception {
        CityUpdateDto updateDto = constructUpdateDto();
        String requestBody = objectMapper.writeValueAsString(updateDto);
        when(cityService.update(updateDto)).thenReturn(Optional.empty());

        this.mockMvc.perform(put("/cities/v1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private CityDto constructCityDto() {
        return new CityDto(CITY_ID,
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(20));
    }
    
    private CityUpdateDto constructUpdateDto() {
        return new CityUpdateDto(CITY_ID,
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(10));
    }
}