package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dao.searchcriteria.RentalOfficeSearchCriteria;
import com.kkorchyts.domain.enums.address.Cities;
import com.kkorchyts.domain.enums.address.Districts;
import com.kkorchyts.domain.enums.address.Streets;
import com.kkorchyts.dto.dtos.RentalOfficeDto;
import com.kkorchyts.dto.dtos.RentalOfficeExtraDetailDto;
import com.kkorchyts.dto.dtos.VehicleDto;
import com.kkorchyts.service.services.rentaloffice.RentalOfficeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/offices")
public class RentalOfficeController {

    private RentalOfficeService rentalOfficeService;
    @ApiOperation(value = "Return list of rental offices", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<Object> getAll(Pageable pageable,
                                         @RequestParam(value = "city", required = false) Cities city,
                                         @RequestParam(value = "district", required = false) Districts district,
                                         @RequestParam(value = "street", required = false) Streets street) {
        return new ResponseEntity<>(rentalOfficeService.getAll(pageable,
                RentalOfficeSearchCriteria.builder()
                        .city(city)
                        .district(district).street(street).
                        build()),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Create new rental office", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<RentalOfficeDto> register(@RequestBody RentalOfficeDto rentalOfficeDto) {
        return new ResponseEntity<>(rentalOfficeService.create(rentalOfficeDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update rental office", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<RentalOfficeDto> update(@RequestBody RentalOfficeDto rentalOfficeDto) {
        rentalOfficeService.update(rentalOfficeDto);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Get rental office information", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<RentalOfficeDto> get(@PathVariable(name = "id") Integer id) {
        RentalOfficeDto rentalOfficeDto = rentalOfficeService.getById(id);
        if (rentalOfficeDto != null) {
            return new ResponseEntity<>(rentalOfficeDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete rental office with moving vehicles to another rental offuce", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Integer id,
                                         @RequestParam(name = "rental_office_to_move") Integer rentalOfficeToMove) {
        // todo validate
        rentalOfficeService.moveVehiclesAndDeleteOffice(id, rentalOfficeToMove);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @ApiOperation(value = "Get rental office vehicles", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/{id}/vehicles")
    @ResponseBody
    public ResponseEntity<Page<VehicleDto>> getRentalOfficeVehicles(@PathVariable(name = "id") Integer id,
                                                                    Pageable pageable,
                                                                    @RequestParam(name = "only_available", defaultValue = "false") Boolean onlyAvailable) {
        // todo validate
        return new ResponseEntity<>(rentalOfficeService.getRentalOfficeVehicles(id, onlyAvailable, pageable), HttpStatus.OK);
    }


    @ApiOperation(value = "Get rental office vehicle models", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/{id}/vehicles/models")
    @ResponseBody
    public ResponseEntity<RentalOfficeExtraDetailDto> getRentalOfficeDetailByVehicleModels(@PathVariable(name = "id") Integer id,
                                                                                           @RequestParam(name = "only_available", defaultValue = "false") Boolean onlyAvailable) {
        // todo validate
        return new ResponseEntity<>(rentalOfficeService.getRentalOfficeDetailByVehicleModels(id, onlyAvailable), HttpStatus.OK);
    }

    @Autowired
    public void setRentalOfficeService(RentalOfficeService rentalOfficeService) {
        this.rentalOfficeService = rentalOfficeService;
    }
}
