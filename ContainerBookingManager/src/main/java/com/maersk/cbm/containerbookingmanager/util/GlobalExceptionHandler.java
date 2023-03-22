package com.maersk.cbm.containerbookingmanager.util;

import com.maersk.cbm.containerbookingmanager.model.enums.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class handles the exception that is thrown by the controller and returns a user readable version of the
 * exceptions. This class can be extended to handle more specific errors.
 * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<ErrorType, List<String>>> handleValidationErrors(WebExchangeBindException ex) {
        log.error(ex.getMessage(), ex);
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(ErrorType.VALIDATION_ERROR, errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<Map<ErrorType, List<String>>> handleValidationErrors(ServerWebInputException ex) {
        log.error(ex.getMessage(), ex);
        Map<ErrorType, List<String>> errorMap;
        if (ex.getMessage().contains("ContainerType")) {
            errorMap = getErrorsMap(ErrorType.VALIDATION_ERROR, Collections.singletonList("container type can only be DRY or REEFER"));
        } else {
            errorMap = getErrorsMap(ErrorType.INPUT_ERROR, Collections.singletonList(ex.getMessage()));
        }
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<ErrorType, String>> handleGeneralExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        Map<ErrorType, String> internalServerError = new HashMap<>();
        internalServerError.put(ErrorType.INTERNAL_SERVER_ERROR, "Sorry there was a problem processing your request");
        return new ResponseEntity<>(internalServerError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<ErrorType, List<String>> getErrorsMap(ErrorType errorType, List<String> errors) {
        Map<ErrorType, List<String>> errorResponse = new HashMap<>();
        errorResponse.put(errorType, errors);
        return errorResponse;
    }
}
