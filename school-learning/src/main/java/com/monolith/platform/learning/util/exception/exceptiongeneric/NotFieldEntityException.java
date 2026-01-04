package com.monolith.platform.learning.util.exception.exceptiongeneric;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFieldEntityException extends RuntimeException{

    private final HttpStatus httpStatus;

    public NotFieldEntityException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}