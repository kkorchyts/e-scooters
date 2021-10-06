package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dto.dtos.LocationDto;
import com.kkorchyts.service.services.location.LocationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/locations")
@Secured({"ROLE_ADMIN"})
public class LocationController {
    private LocationService locationService;

    @ApiOperation(value = "Return list of locations", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<Object> getAll(Pageable pageable) {
        return new ResponseEntity<>(locationService.getAll(pageable,
                null),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Create new location", authorizations = {@Authorization(value = "jwtToken")})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<LocationDto> register(@RequestBody LocationDto locationDto) {
        return new ResponseEntity<>(locationService.create(locationDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update location", authorizations = {@Authorization(value = "jwtToken")})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<LocationDto> update(@RequestBody LocationDto locationDto) {
        locationService.update(locationDto);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Get location information", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<LocationDto> get(@PathVariable(name = "id") Integer locationId) {
        LocationDto locationDto = locationService.getById(locationId);
        if (locationDto != null) {
            return new ResponseEntity<>(locationDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }
}
