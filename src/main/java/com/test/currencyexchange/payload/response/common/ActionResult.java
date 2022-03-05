package com.test.currencyexchange.payload.response.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ActionResult<T> {
    private Integer statusCode = 200;
    private String message;
    private T result;
    private Map<String, String> errors;

    public ActionResult(){}

    public ActionResult(Integer httpCode, String message, Map<String, String> details, T result){
        this.statusCode = httpCode;
        this.message = message;
        this.errors = details;
        this.result = result;
    }
}
