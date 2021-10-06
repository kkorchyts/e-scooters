package com.kkorchyts.service.validators;

import com.kkorchyts.dao.repositories.tariff.TariffDao;
import com.kkorchyts.domain.entities.Tariff;
import com.kkorchyts.service.exception.IncorrectDataException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.kkorchyts.service.utils.CommonUtils.throwAndLogException;

@Component
public class TariffValidator {
    private static final Logger logger = LoggerFactory.getLogger(
            TariffValidator.class);

    private SessionFactory sessionFactory;
    private TariffDao tariffDao;

    public void validateTariffById(Integer tariffId) {
        if (tariffId == null) {
            throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect input Tariff Id (tariff id = null)");
        }
        Session session = sessionFactory.getCurrentSession();

        Tariff tariff = tariffDao.findById(tariffId);
        if (tariff == null) {
            throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect input Tariff Id (tariff id = " + tariffId + ")");
        }
        session.detach(tariff);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }
}
