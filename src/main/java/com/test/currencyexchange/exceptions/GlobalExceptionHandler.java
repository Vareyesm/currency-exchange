package com.test.currencyexchange.exceptions;

import com.test.currencyexchange.payload.response.common.ActionResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ActionResult<Object> actionResult = new ActionResult<>();
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach((error) -> errors.put(error.getField(), error.getDefaultMessage()));

        actionResult.setMessage("Invalid data");
        actionResult.setErrors(errors);
        actionResult.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(actionResult, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ActionResult<Object> actionResult = new ActionResult<>();
        Map<String, String> errors = new HashMap<>();

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        errors.put("global", ex.getCause().getMessage());
        actionResult.setMessage("An error has occured");
        actionResult.setErrors(errors);
        actionResult.setStatusCode(status.value());

        return new ResponseEntity<>(actionResult, headers, status);
    }
}
