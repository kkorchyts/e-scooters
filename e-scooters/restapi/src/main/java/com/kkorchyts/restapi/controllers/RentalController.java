package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dao.searchcriteria.RentalsSearchCriteria;
import com.kkorchyts.dto.dtos.RentalDto;
import com.kkorchyts.dto.dtos.RentalFinishDto;
import com.kkorchyts.dto.dtos.RentalStartDto;
import com.kkorchyts.restapi.validators.AccessValidator;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.kkorchyts.restapi.validators.UsersUtils.getAuthenticatedUserLogin;
import static com.kkorchyts.restapi.validators.UsersUtils.getAuthentication;
import static com.kkorchyts.restapi.validators.UsersUtils.isAdmin;

@RestController
@RequestMapping(value = "/rentals")
public class RentalController {
    private VehicleService vehicleService;
    private UserService userService;
    private RentalService rentalService;

    @ApiOperation(value = "Return list of rentals", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<RentalDto>> getRentals(Pageable pageable) {
        if (!isAdmin(getAuthentication())) {
            return new ResponseEntity<>(rentalService.getAll(pageable,
                    RentalsSearchCriteria.builder().login(getAuthenticatedUserLogin()).build()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rentalService.getAll(pageable,
                    RentalsSearchCriteria.builder().build()),
                    HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Return information about rental", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<RentalDto> getRental(@PathVariable(name = "id") Integer rentalId) {
        if (isAdmin(getAuthentication())) {
            return new ResponseEntity<>(rentalService.getById(rentalId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rentalService.getByLoginAndId(getAuthenticatedUserLogin(), rentalId), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Rent an available vehicle", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping
    @ResponseBody
    public ResponseEntity<RentalDto> rentVehicle(@RequestBody RentalStartDto rentalStartDto) {
        AccessValidator.validateLimitedAccess(getAuthentication(), getAuthenticatedUserLogin());
        rentalStartDto.setUserId(userService.findByLogin(getAuthenticatedUserLogin()).getId());
        return new ResponseEntity<>(rentalService.start(rentalStartDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Finish a rental", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PatchMapping
    @ResponseBody
    // TODO отдельный типа finishVehicleRentDto
    public ResponseEntity<Object> finishVehicleRent(@RequestBody RentalFinishDto rentalFinishDto) {
        rentalService.finish(userService.findByLogin(getAuthenticatedUserLogin()).getId(), rentalFinishDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
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
