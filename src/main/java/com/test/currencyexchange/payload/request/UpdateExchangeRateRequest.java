package com.test.currencyexchange.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateExchangeRateRequest {
    @NotNull(message = "Exchange id is required")
    private Integer id;
    @NotNull(message = "The value to change is required")
    private Double value;
}
