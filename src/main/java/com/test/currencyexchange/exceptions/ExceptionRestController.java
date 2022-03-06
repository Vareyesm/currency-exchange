package com.test.currencyexchange.exceptions;

import com.test.currencyexchange.payload.response.common.ActionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionRestController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ActionResult<Object>> handleCustomException(CustomException ex) {
        ActionResult<Object> result = ex.getResult();
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatusCode()));
    }
}
