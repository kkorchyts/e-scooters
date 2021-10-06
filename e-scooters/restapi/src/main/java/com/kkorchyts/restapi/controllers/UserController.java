package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dao.searchcriteria.RentalsSearchCriteria;
import com.kkorchyts.dao.searchcriteria.UserSearchCriteria;
import com.kkorchyts.dto.dtos.RentalDto;
import com.kkorchyts.dto.dtos.UserDto;
import com.kkorchyts.restapi.validators.AccessValidator;
import com.kkorchyts.service.services.rental.RentalService;
import com.kkorchyts.service.services.user.UserService;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.kkorchyts.restapi.validators.UsersUtils.getAuthentication;
import static com.kkorchyts.restapi.validators.UsersUtils.isAdmin;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;
    private RentalService rentalService;

    private PasswordEncoder passwordEncoder;

    private void encodePassword(UserDto userDto) {
        if (userDto.getPassword() != null && userDto.getPassword().length() > 0) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
    }

    @ApiOperation(value = "Return list of users", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<Object> getAll(Pageable pageable) {
        return new ResponseEntity<>(userService.getAll(pageable,
                UserSearchCriteria.builder().build()),
                HttpStatus.OK);
    }

    /* tests:
        admin logged in - any user can be created
        user logged in - exception. User should be logouted
        logouted, registration with ADMIN roles - exception. user hasn't have admin role
        logouted, registration without ADMIN roles - ok (can be constraint exceptions)
    */
    @ApiOperation(value = "Register a new user", authorizations = {@Authorization(value = "jwtToken")})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        AccessValidator.validateRegisterAction(userDto);
        encodePassword(userDto);
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }


    /* tests:
        admin logged in, id and login have to be same
        user logged in, own id and login, tries to set up admin role - exception, user hasn't have admin role
        user logged in, own id and login, doesn't try to set up admin role - ok
        user logged in, his login, another id - exception
        user logged in, another id, another login - exception
    */
    @ApiOperation(value = "Update user information", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        AccessValidator.validateUpdateUserAction(userDto);
        encodePassword(userDto);
        userService.update(userDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /* tests:
        admin logged in  - can get any information
        user logged in, his login - ok
        user logged in, another login - exception
    */
    @ApiOperation(value = "Return user information", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{login}")
    @ResponseBody
    public ResponseEntity<UserDto> getByLogin(@PathVariable(name = "login") @NotNull String login) {
        if (!isAdmin(getAuthentication())) {
            AccessValidator.validateLimitedAccess(getAuthentication(), login);
        }

        return new ResponseEntity<>(userService.findByLogin(login),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Return user rentals", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{login}/rentals")
    @ResponseBody
    public ResponseEntity<Page<RentalDto>> getRentlas(@PathVariable(name = "login") @NotNull String login,
                                                      Pageable pageable) {
        if (!isAdmin(getAuthentication())) {
            AccessValidator.validateLimitedAccess(getAuthentication(), login);
        }

        RentalsSearchCriteria rentalsSearchCriteria = RentalsSearchCriteria.builder().login(login).build();
        return new ResponseEntity<>(rentalService.getAll(pageable, rentalsSearchCriteria), HttpStatus.OK);
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }
}
