package com.kkorchyts.restapi.validators;

import com.kkorchyts.domain.enums.UserRole;
import com.kkorchyts.dto.dtos.UserDto;
import com.kkorchyts.service.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.kkorchyts.restapi.validators.UsersUtils.getAuthentication;
import static com.kkorchyts.restapi.validators.UsersUtils.isAdmin;

@Component
public class AccessValidator {
    private static final Logger logger = LoggerFactory.getLogger(
            AccessValidator.class);

    private static UserService userService;

    public static void validateLimitedAccess(Authentication authentication, String login) {
        if (isAdmin(authentication)) {
            return;
        }

        String userLogin = authentication.getName();
        if (login == null) {
            throwAccessException("Login hasn't to be null!");
        }

        if (!userLogin.toLowerCase().equals(login.toLowerCase())) {
            throwInvalidCredentialsException("User '" + login + "' without ADMIN role tries to get information of another user (login = " + login + ")");
        }
    }

    public static void validateUserNotAdmin(UserDto userDto) {
        if (userDto.getRoles() != null && userDto.getRoles().stream()
                .anyMatch(role -> role.equals(UserRole.ADMIN))) {
            throwInvalidCredentialsException("Impossible to grant ADMIN role for new user (login = " + userDto.getLogin() + ")");
        }
    }

    public static void validateRegisterAction(UserDto userDto) {
        if (!isAdmin(getAuthentication())) {
            if (!(getAuthentication() instanceof AnonymousAuthenticationToken)) {
                throwNeedLogoutException("You need logout before register!");
            }
            validateUserNotAdmin(userDto);
        }
    }

    public static void validateUpdateUserAction(UserDto userDto) {
        validateLimitedAccess(getAuthentication(), userDto.getLogin());
        if (!isAdmin(getAuthentication())) {
            validateUserNotAdmin(userDto);
        }
        UserDto originalUserDtoForUpdate = userService.findByLogin(userDto.getLogin());
        if (!originalUserDtoForUpdate.getId().equals(userDto.getId())) {
            throwAccessException(userDto.getLogin());
        }
    }

    private static void throwAccessException(String userLogin) {
        String message = "User doesn't have an access (User Name = '" + userLogin + "')";
        logger.error(message);
        throw new AccessDeniedException(message);
    }

    private static void throwInvalidCredentialsException(String message) {
        logger.error(message);
        throw new AccessDeniedException(message);
    }

    private static void throwNeedLogoutException(String message) {
        logger.error(message);
        throw new RuntimeException(message);
    }

    @Autowired
    public void setUserService(UserService userService) {
        AccessValidator.userService = userService;
    }
}
