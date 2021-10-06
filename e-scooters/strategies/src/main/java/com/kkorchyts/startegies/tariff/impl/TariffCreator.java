package com.kkorchyts.startegies.tariff.impl;

import com.kkorchyts.domain.entities.Tariff;
import com.kkorchyts.startegies.exceptions.TarifficatorError;
import com.kkorchyts.startegies.tariff.TariffStrategy;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class TariffCreator {

    private static Integer getTimelyUnitCount(LocalDateTime from, LocalDateTime to, Integer secondsPerUnit) {
        Duration duration = Duration.between(from, to);
        Long periodInSeconds = duration.getSeconds();
        if (periodInSeconds % secondsPerUnit > 0) {
            return (int) (periodInSeconds / secondsPerUnit + 1);
        } else {
            return (int) (periodInSeconds / secondsPerUnit);
        }
    }

    public static TariffStrategy create(Tariff tariff) {
        switch (tariff.getPerUnitTime()) {
            case DAYS:
                return (startDateTime, finishDateTime) ->
                        tariff.getTimelyPrice().multiply(new BigDecimal(getTimelyUnitCount(startDateTime, finishDateTime, 60 * 60 * 24)));
            case HOURS:
                return (startDateTime, finishDateTime) ->
                        tariff.getTimelyPrice().multiply(new BigDecimal(getTimelyUnitCount(startDateTime, finishDateTime, 60 * 60)));
            case MINUTES:
                return (startDateTime, finishDateTime) ->
                        tariff.getTimelyPrice().multiply(new BigDecimal(getTimelyUnitCount(startDateTime, finishDateTime, 60)));
            default:
                throw new TarifficatorError("Incorrect tarifficator (tariff id = " + tariff.getId() + ")");
        }
    }
}
