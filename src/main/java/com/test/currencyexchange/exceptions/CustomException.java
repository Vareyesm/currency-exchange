package com.test.currencyexchange.exceptions;

import com.test.currencyexchange.payload.response.common.ActionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends Throwable {
    private ActionResult<Object> result;
}
