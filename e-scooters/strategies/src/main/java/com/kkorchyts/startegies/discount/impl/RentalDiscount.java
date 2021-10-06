package com.kkorchyts.startegies.discount.impl;

import com.kkorchyts.domain.entities.Discount;
import com.kkorchyts.startegies.discount.Discounter;
import com.kkorchyts.startegies.exceptions.DiscounterError;

import java.math.BigDecimal;

public class RentalDiscount {
    public static Discounter create(Discount discount) {
        if (discount == null) {
            return cost -> new BigDecimal(0);
        } else {
            return cost -> cost.multiply(BigDecimal.valueOf(discount.getPercentage())).divide(BigDecimal.valueOf(100));
        }
    }
}
