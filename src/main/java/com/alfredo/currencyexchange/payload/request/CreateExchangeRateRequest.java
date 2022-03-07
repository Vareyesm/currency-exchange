package com.alfredo.currencyexchange.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateExchangeRateRequest {
    @NotNull(message = "The value to change is required")
    private Double value;
    @NotNull(message = "The source currency is required")
    private Integer sourceCurrencyId;
    @NotNull(message = "The destination currency is required")
    private Integer destinationCurrencyId;
}
