package com.kkorchyts.startegies.discount;

import java.math.BigDecimal;

public interface Discounter {
    BigDecimal getDiscountAmount(BigDecimal cost);
}
