package com.kkorchyts.dto.converters;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DtoUtils {
    protected DtoUtils() {
    }

    public static <T> void setIfNotNull(final Supplier<T> getter, final Consumer<T> setter) {
        T t = getter.get();
        if (null != t) {
            setter.accept(t);
        }
    }
}
