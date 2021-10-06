package com.kkorchyts.service.validators;

import com.kkorchyts.dao.repositories.user.UserDao;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.dto.dtos.UserDto;
import com.kkorchyts.service.exception.IncorrectDataException;
import com.kkorchyts.service.utils.CommonUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class UserValidator {
    private static final Logger logger = LoggerFactory.getLogger(
            UserValidator.class);

    private UserDao userDao;
    private SessionFactory sessionFactory;

    public void validateExistingUserDto(UserDto userDto) {
        Session session = sessionFactory.getCurrentSession();

        if (userDto.getId() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "User id is missing!");
        }

        if (userDto.getLogin() == null || userDto.getLogin().length() == 0) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "User login is missing!");
        }

        User user = userDao.findById(userDto.getId());
        if (user == null) {
            CommonUtils.throwAndLogException(EntityNotFoundException.class, logger,
                    "User not found (user id = " + userDto.getId() + ")");
        }

        String login = user.getLogin();
        session.detach(user);
        if (!login.equals(userDto.getLogin())) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "User login is wrong (user id = " + userDto.getId() + "; user login = " + userDto.getLogin() + " )!");
        }
    }

    public void validateNewUserDto(UserDto userDto) {
        Session session = sessionFactory.getCurrentSession();

        if (userDto.getLogin() == null || userDto.getLogin().length() == 0) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "User login is missing!");
        }

        if (userDao.isExists(userDto.getLogin())) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "User already exists (user login = " + userDto.getLogin() + " )!");
        }
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
