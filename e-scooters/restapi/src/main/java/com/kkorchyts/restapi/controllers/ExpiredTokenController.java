package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dto.dtos.ExpiredTokenDto;
import com.kkorchyts.service.services.expiredtoken.ExpiredTokenService;
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
@RequestMapping(value = "/tokens")
public class ExpiredTokenController {

    private ExpiredTokenService expiredTokenService;

    @ApiOperation(value = "Return list of expired tokens", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<ExpiredTokenDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(expiredTokenService.getAll(pageable, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new expired token", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<ExpiredTokenDto> register(@RequestBody ExpiredTokenDto expiredTokenDto) {
        return new ResponseEntity<>(expiredTokenService.create(expiredTokenDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update expired token", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<ExpiredTokenDto> update(@RequestBody ExpiredTokenDto expiredTokenDto) {
        expiredTokenService.update(expiredTokenDto);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Get expired token information", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ExpiredTokenDto> get(@PathVariable(name = "id") Integer id) {
        ExpiredTokenDto expiredTokenDto = expiredTokenService.getById(id);
        if (expiredTokenDto != null) {
            return new ResponseEntity<>(expiredTokenDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete expired token", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Integer id) {
        // todo validate
        expiredTokenService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Autowired
    public void setExpiredTokenService(ExpiredTokenService expiredTokenService) {
        this.expiredTokenService = expiredTokenService;
    }
}