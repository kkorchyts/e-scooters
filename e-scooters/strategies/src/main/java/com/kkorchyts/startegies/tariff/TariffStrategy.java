package com.kkorchyts.startegies.tariff;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TariffStrategy {
    BigDecimal getCost(LocalDateTime startDateTime, LocalDateTime finishDateTime);
}
