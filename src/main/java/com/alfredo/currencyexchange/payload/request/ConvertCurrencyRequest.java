package com.alfredo.currencyexchange.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ConvertCurrencyRequest {
    @NotNull(message = "Source currency is required")
    private Integer sourceCurrencyId;
    @NotNull(message = "TDestination currency is required")
    private Integer destinationCurrencyId;
    @NotNull(message = "Amount to convert is required")
    private Double amountConvert;
    @NotNull(message = "Exchange rate value is required")
    private Double exchangeRateValue;
}
