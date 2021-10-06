package com.kkorchyts.service.services.user;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.domain.enums.UserRole;
import com.kkorchyts.dto.dtos.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> getAll(Pageable pageable, SearchCriteria<User> searchCriteria);

    void update(UserDto userDto);

    UserDto create(UserDto userDto);

    UserDto findByLogin(String login);

    void removeRole(UserDto userDto, UserRole userRole);

    void grantRole(UserDto userDto, UserRole userRole);
}
