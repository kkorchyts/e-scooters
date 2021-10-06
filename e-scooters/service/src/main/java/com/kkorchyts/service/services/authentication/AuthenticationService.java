package com.kkorchyts.service.services.authentication;

public interface AuthenticationService {
    String createAuthenticationToken(String login, String password);

    void disableAuthenticationToken(String token);
}
