package com.home.citylist.controllers;

import com.home.citylist.dto.CityDto;
import com.home.citylist.dto.CityUpdateDto;
import com.home.citylist.facades.CityFacade;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cities", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CityController {

    private final CityFacade cityFacade;

    public CityController(CityFacade cityFacade) {
        this.cityFacade = cityFacade;
    }

    @GetMapping("v1")
    public List<CityDto> getCities(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        return cityFacade.getAll(page, size);
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<CityDto> getCity(@PathVariable long id) {
        return cityFacade.getCity(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/v1/name/{name}")
    public ResponseEntity<CityDto> getByName(@PathVariable String name) {
        return cityFacade.getByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("v1")
    public ResponseEntity<CityDto> update(@RequestBody @Valid CityUpdateDto updateDto) {
        return cityFacade.update(updateDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
