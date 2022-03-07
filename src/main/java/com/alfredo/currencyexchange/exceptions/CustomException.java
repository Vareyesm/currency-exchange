package com.alfredo.currencyexchange.exceptions;

import com.alfredo.currencyexchange.payload.response.common.ActionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends Throwable {
    private ActionResult<Object> result;
}
