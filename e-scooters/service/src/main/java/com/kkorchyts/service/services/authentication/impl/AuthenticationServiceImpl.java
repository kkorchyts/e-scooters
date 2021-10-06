package com.kkorchyts.service.services.authentication.impl;

import com.kkorchyts.dto.dtos.ExpiredTokenDto;
import com.kkorchyts.jwt.JwtUtil;
import com.kkorchyts.service.services.authentication.AuthenticationService;
import com.kkorchyts.service.services.expiredtoken.ExpiredTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private ExpiredTokenService expiredTokenService;

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtTokenUtil;

    @Override
    public String createAuthenticationToken(String login, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        return jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
    }

    @Override
    public void disableAuthenticationToken(String token) {
        expiredTokenService.create(new ExpiredTokenDto(token));
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(JwtUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setExpiredTokenService(ExpiredTokenService expiredTokenService) {
        this.expiredTokenService = expiredTokenService;
    }
}
