package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dao.searchcriteria.RentalsSearchCriteria;
import com.kkorchyts.dao.searchcriteria.VehicleSearchCriteria;
import com.kkorchyts.dto.dtos.RentalDto;
import com.kkorchyts.dto.dtos.VehicleDto;
import com.kkorchyts.service.services.rental.RentalService;
import com.kkorchyts.service.services.user.UserService;
import com.kkorchyts.service.services.vehicle.VehicleService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {
    private VehicleService vehicleService;
    private UserService userService;
    private RentalService rentalService;

    @ApiOperation(value = "Return list of vehicles", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<VehicleDto>> getVehicles(Pageable pageable,
                                                        @RequestParam(name = "only_available", defaultValue = "false") Boolean onlyAvailable) {
        if (onlyAvailable) {
            return new ResponseEntity<>(vehicleService.getAllVehicles(pageable, VehicleSearchCriteria.builder().available(true).build()), HttpStatus.OK);
            //return new ResponseEntity<>(vehicleService.getAvailableVehicles(pageable, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(vehicleService.getAllVehicles(pageable, VehicleSearchCriteria.builder().build()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Create new vehicle", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<VehicleDto> register(@RequestBody VehicleDto vehicleDto) {
        return new ResponseEntity<>(vehicleService.create(vehicleDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update vehicle", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<VehicleDto> update(@RequestBody VehicleDto vehicleDto) {
        vehicleService.update(vehicleDto);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Get vehicle information", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<VehicleDto> get(@PathVariable(name = "id") Integer id) {
        VehicleDto vehicleDto = vehicleService.getById(id);
        if (vehicleDto != null) {
            return new ResponseEntity<>(vehicleDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete vehicle", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Integer id) {
        // todo validate
        vehicleService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @ApiOperation(value = "Get vehicle rentals ", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/{id}/rentals")
    @ResponseBody
    public ResponseEntity<Page<RentalDto>> getVehicleRentals(@PathVariable(name = "id") Integer id,
                                                             Pageable pageable) {
        // todo validate
        RentalsSearchCriteria rentalsSearchCriteria = RentalsSearchCriteria.builder().vehicle(id).build();
        return new ResponseEntity<>(rentalService.getAll(pageable, rentalsSearchCriteria), HttpStatus.OK);
    }


    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }
}
