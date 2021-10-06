package com.kkorchyts.service.validators;

import com.kkorchyts.dao.repositories.discount.DiscountDao;
import com.kkorchyts.domain.entities.Discount;
import com.kkorchyts.service.exception.IncorrectDataException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.kkorchyts.service.utils.CommonUtils.throwAndLogException;

@Component
public class DiscountValidator {
    private static final Logger logger = LoggerFactory.getLogger(
            DiscountValidator.class);

    private SessionFactory sessionFactory;
    private DiscountDao discountDao;

    public void validateDiscountById(Integer discountId) {
        if (discountId == null) {
            throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect input Discount Id (discount id = null)");
        }
        Session session = sessionFactory.getCurrentSession();

        Discount discount = discountDao.findById(discountId);
        if (discount == null) {
            throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect input Discount Id (discount id = " + discountId + ")");
        }
        session.detach(discount);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setDiscountDao(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }
}
