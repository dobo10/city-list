package com.home.citylist.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.citylist.dto.CityDto;
import com.home.citylist.dto.CityUpdateDto;
import com.home.citylist.facades.CityFacade;
import com.home.citylist.services.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.home.citylist.common.TestUtils.constructCityDto;
import static com.home.citylist.common.TestUtils.constructUpdateDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
class CityControllerTest {

    private static final String API_ROOT_PATH = "/cities";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    @MockBean
    private CityFacade cityFacade;

    @Test
    void getAllCities_shouldReturnCitiesWithStatusOk() throws Exception {
        CityDto firstCity = constructCityDto();
        CityDto secondCity = constructCityDto();
        List<CityDto> cityDtoList = Arrays.asList(firstCity, secondCity);
        when(cityFacade.getAll(0, 5)).thenReturn(cityDtoList);

        MvcResult result = this.mockMvc.perform(get(API_ROOT_PATH + "/v1"))
                .andExpect(status().isOk()).andReturn();

        List<CityDto> citiesFound = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(citiesFound.size()).isEqualTo(cityDtoList.size());
        assertThat(citiesFound.get(0).id()).isEqualTo(firstCity.id());
        assertThat(citiesFound.get(0).name()).isEqualTo(firstCity.name());
        assertThat(citiesFound.get(1).imageUrl()).isEqualTo(secondCity.imageUrl());
    }

    @Test
    void getAllCities_whenNoCitiesFound_shouldReturnStatusNotFound() throws Exception {
        when(cityService.getAll(0, 5)).thenReturn(new ArrayList<>());

        MvcResult result = this.mockMvc.perform(get(API_ROOT_PATH + "/v1"))
                .andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentLength()).isZero();
    }

    @Test
    void getCity_shouldReturnCityWithStatusOk() throws Exception {
        CityDto cityDto = constructCityDto();
        when(cityFacade.getCity(cityDto.id())).thenReturn(Optional.of(cityDto));

        MvcResult result = this.mockMvc.perform(get(API_ROOT_PATH + "/v1/{id}", cityDto.id()))
                .andExpect(status().isOk()).andReturn();

        CityDto cityFound = objectMapper.readValue(result.getResponse().getContentAsString(), CityDto.class);
        assertThat(cityFound.id()).isEqualTo(cityDto.id());
        assertThat(cityFound.name()).isEqualTo(cityDto.name());
        assertThat(cityFound.imageUrl()).isEqualTo(cityDto.imageUrl());
    }

    @Test
    void getCity_whenNotFoundById_shouldReturnStatusNotFound() throws Exception {
        when(cityService.getById(1)).thenReturn(Optional.empty());

        this.mockMvc.perform(get(API_ROOT_PATH + "/v1/{id}", 1)).andExpect(status().isNotFound());
    }

    @Test
    void getByName_shouldReturnCityWithStatusOk() throws Exception {
        CityDto cityDto = constructCityDto();
        when(cityFacade.getByName(any())).thenReturn(Optional.of(cityDto));

        MvcResult result = this.mockMvc.perform(get(API_ROOT_PATH + "/v1/name/{name}", cityDto.name()))
                .andExpect(status().isOk()).andReturn();

        CityDto cityFound = objectMapper.readValue(result.getResponse().getContentAsString(), CityDto.class);
        assertThat(cityFound.id()).isEqualTo(cityDto.id());
        assertThat(cityFound.name()).isEqualTo(cityDto.name());
        assertThat(cityFound.imageUrl()).isEqualTo(cityDto.imageUrl());
    }

    @Test
    void getByName_whenCityNotfound_shouldReturnStatusNotFound() throws Exception {
        when(cityService.getByName(any())).thenReturn(Optional.empty());

        this.mockMvc.perform(get(API_ROOT_PATH + "/v1/name/testCity")).andExpect(status().isNotFound());
    }

    @Test
    void update_shouldReturnUpdatedCityWithStatusOk() throws Exception {
        CityDto cityDto = constructCityDto();
        CityUpdateDto updateDto = constructUpdateDto();
        String requestBody = objectMapper.writeValueAsString(updateDto);
        when(cityFacade.update(updateDto)).thenReturn(Optional.of(cityDto));

        MvcResult result = this.mockMvc.perform(put(API_ROOT_PATH + "/v1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        CityDto cityFound = objectMapper.readValue(result.getResponse().getContentAsString(), CityDto.class);
        assertThat(cityFound.id()).isEqualTo(cityDto.id());
        assertThat(cityFound.name()).isEqualTo(cityDto.name());
        assertThat(cityFound.imageUrl()).isEqualTo(cityDto.imageUrl());
    }

    @Test
    void update_whenRequestBodyNotValid_shouldReturnBadRequest() throws Exception {
        CityUpdateDto updateDto = new CityUpdateDto(1, "", "");
        String requestBody = objectMapper.writeValueAsString(updateDto);

        this.mockMvc.perform(put(API_ROOT_PATH + "/v1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_whenCityNotfound_shouldReturnStatusNotFound() throws Exception {
        CityUpdateDto updateDto = constructUpdateDto();
        String requestBody = objectMapper.writeValueAsString(updateDto);
        when(cityFacade.update(updateDto)).thenReturn(Optional.empty());

        this.mockMvc.perform(put(API_ROOT_PATH + "/v1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}