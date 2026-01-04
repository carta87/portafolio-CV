package com.monolith.platform.learning.util.exception;

import com.monolith.platform.learning.util.exception.exceptiongeneric.NotFieldEntityException;
import org.springframework.http.HttpStatus;

public final class ExceptionUtil {
    private ExceptionUtil(){
    }

    public static void getIllegalArgumentException(Long id, String message) {
        if (id == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void getIllegalArgumentException(String message) {
            throw new IllegalArgumentException(message);
    }

    public static void getNotFieldEntityException(Long id, String message, HttpStatus httpStatus) {
        if (id == null) {
            throw new NotFieldEntityException(message, httpStatus);
        }
    }
}
