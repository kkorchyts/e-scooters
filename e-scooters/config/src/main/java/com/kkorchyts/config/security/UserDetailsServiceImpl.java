package com.kkorchyts.config.security;

import com.kkorchyts.domain.enums.UserRole;
import com.kkorchyts.dto.dtos.UserDto;
import com.kkorchyts.service.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            UserDto userDto = userService.findByLogin(s);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userDto.getLogin())
                    .password(userDto.getPassword())
                    .roles(userDto.getRoles().stream().map(UserRole::getValue).toArray(String[]::new))
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
