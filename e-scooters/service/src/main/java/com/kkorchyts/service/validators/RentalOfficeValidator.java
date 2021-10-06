package com.kkorchyts.service.validators;

import com.kkorchyts.dto.dtos.RentalOfficeDto;
import com.kkorchyts.service.exception.IncorrectDataException;
import com.kkorchyts.service.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RentalOfficeValidator {
    private static final Logger logger = LoggerFactory.getLogger(
            LocationValidator.class);

    public void validateRentalOfficeDataForUpdate(RentalOfficeDto rentalOfficeDto) {
        if (rentalOfficeDto.getId() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger, "Rental office id can't be null!");
        }
    }

    public void validateNewRentalOfficeData(RentalOfficeDto rentalOfficeDto) {
        if (rentalOfficeDto.getName() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger, "Rental office name can't be null!");
        }

        if (rentalOfficeDto.getLocationId() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger, "Rental office Location id can't be null!");
        }

        if (rentalOfficeDto.getLatitude() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger, "Rental office Latitude can't be null!");
        }

        if (rentalOfficeDto.getLongtitude() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger, "Rental office Longtitude can't be null!");
        }
    }
}
