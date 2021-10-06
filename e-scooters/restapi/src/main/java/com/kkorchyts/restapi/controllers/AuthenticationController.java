package com.kkorchyts.restapi.controllers;

import com.kkorchyts.jwt.AuthRequest;
import com.kkorchyts.jwt.AuthResponse;
import com.kkorchyts.service.services.authentication.AuthenticationService;
import com.mysql.cj.exceptions.WrongArgumentException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            return new AuthResponse(authenticationService.createAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Имя или пароль неправильны", e);
        }
    }

    @ApiOperation(value = "Get discount information", authorizations = {@Authorization(value = "jwtToken")})
    @PostMapping("/exit")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> disableAuthenticationToken(@RequestHeader("Authorization") @ApiIgnore String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            authenticationService.disableAuthenticationToken(token);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            throw new WrongArgumentException("Token not found!");
        }
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}