package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dto.dtos.VehicleModelDto;
import com.kkorchyts.service.services.vehiclemodel.VehicleModelService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/models")
public class VehicleModelController {

    private VehicleModelService vehicleModelService;

    @ApiOperation(value = "Return list of vehicle models", authorizations = {@Authorization(value = "jwtToken")})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<VehicleModelDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(vehicleModelService.getAll(pageable, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new vehicle model", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<VehicleModelDto> register(@RequestBody VehicleModelDto vehicleModelDto) {
        return new ResponseEntity<>(vehicleModelService.create(vehicleModelDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update vehicle model", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<VehicleModelDto> update(@RequestBody VehicleModelDto vehicleModelDto) {
        vehicleModelService.update(vehicleModelDto);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Get vehicle model information", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<VehicleModelDto> get(@PathVariable(name = "id") Integer id) {
        VehicleModelDto vehicleModelDto = vehicleModelService.getById(id);
        if (vehicleModelDto != null) {
            return new ResponseEntity<>(vehicleModelDto, HttpStatus.OK);
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
        vehicleModelService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Autowired
    public void setVehicleModelService(VehicleModelService vehicleModelService) {
        this.vehicleModelService = vehicleModelService;
    }
}
