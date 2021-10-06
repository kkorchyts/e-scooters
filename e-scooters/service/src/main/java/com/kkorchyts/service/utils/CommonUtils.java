package com.kkorchyts.service.utils;

import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CommonUtils {
    public static <E extends RuntimeException> void throwAndLogException(Class<E> eClass, Logger logger, String message) throws RuntimeException {
        try {
            Constructor<?> ctr = eClass.getConstructor(String.class);
            RuntimeException exception = (RuntimeException) ctr.newInstance(message);
            logger.error(message);
            throw exception;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            String newMessage = "Validator exception (message : " + message + "; \nerror: " + e.getMessage();
            logger.error(newMessage);
            throw new RuntimeException(newMessage);
        }
    }

}
