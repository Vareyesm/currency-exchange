package com.test.currencyexchange.payload.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionResult<T> {
    private Integer statusCode = 200;
    private String message;
    private T result;
    private Map<String, String> errors = new HashMap<>();
}
