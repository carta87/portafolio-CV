package com.monolith.platform.learning.util.exception.advice;

import com.monolith.platform.learning.domain.dto.util.ErrorDTO;
import com.monolith.platform.learning.util.ConstantAuth;
import com.monolith.platform.learning.util.ConstantCourse;
import com.monolith.platform.learning.util.ConstantGeneral;
import com.monolith.platform.learning.util.exception.exceptiongeneric.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException ex){
        if (ex.getMessage().contains("You exceeded your current quota")){
            return factoryResponseErrorDto("OpenAI-exceededYourCurrentQuotaException",
                    ConstantGeneral.ERROR_OPEN_IA_CURRENT_QUOTA,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (ex.getMessage().contains("Incorrect API key provided")) {
            return factoryResponseErrorDto("OpenAI-incorrectApiKeyException",
                    ConstantGeneral.ERROR_OPEN_IA_API_KEY_INCORRECT,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return factoryResponseErrorDto("runtimeException",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NotUsernameFoundException.class)
    public ResponseEntity<ErrorDTO> usernameNotFoundExceptionHandler(NotUsernameFoundException ex){
        return factoryResponseErrorDto("usernameNotFoundException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> illegalArgumentExceptionHandler(IllegalArgumentException ex){
        return new ResponseEntity<>(ErrorDTO.builder()
                        .type("illegalArgumentException")
                        .message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException  ex){
        if (ex.getMessage().contains("Duplicate entry")){
            return factoryResponseErrorDto("dataIntegrityViolationException-V1",
                    ConstantAuth.ERROR_USERNAME_COMMON,
                    HttpStatus.NOT_ACCEPTABLE);
        } else if (ex.getMessage().contains("value in column \"course_number\" of relation \"students\"")) {
            return factoryResponseErrorDto("dataIntegrityViolationException-V2",
                    ConstantCourse.ERROR_COURSE_REGISTERED_GENERAL,
                    HttpStatus.BAD_REQUEST);
            
        }
        return factoryResponseErrorDto("dataIntegrityViolationException", ex.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ErrorDTO> nullPointerExceptionHandler(NullPointerException  ex){
        if (ex.getMessage().contains("because \"password\" is null")){
            return factoryResponseErrorDto("nullPointerException",
                    ConstantAuth.ERROR_FALL_PASSWORD,
                    HttpStatus.NOT_ACCEPTABLE);
        }
        return factoryResponseErrorDto("nullPointerException", ex.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = DuplicateNumberIdEntityException.class)
    public ResponseEntity<ErrorDTO> duplicateNumberIdEntityExceptionHandler(DuplicateNumberIdEntityException ex) {
        return factoryResponseErrorDto("duplicateNumberIdEntityException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFieldEntityException.class)
    public ResponseEntity<ErrorDTO> notNumberIdEntityExceptionHandler(NotFieldEntityException ex) {
        return factoryResponseErrorDto("notFieldEntityException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IdNotRequiredEntityException.class)
    public ResponseEntity<ErrorDTO> idNotRequiredEntityExceptionHandler(IdNotRequiredEntityException ex) {
        return factoryResponseErrorDto("idNotRequiredEntityException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorDTO> notFoundExceptionHandler(NotFoundException ex) {
        return factoryResponseErrorDto("notFoundException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<ErrorDTO> factoryResponseErrorDto(String name, String message, HttpStatus status) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .type(name)
                .message(message).build(), status);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDTO>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        List<ErrorDTO> errorDTOList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach( error ->
                errorDTOList.add(ErrorDTO.builder()
                        .type(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
        );
        return ResponseEntity.badRequest().body(errorDTOList);
    }
}
