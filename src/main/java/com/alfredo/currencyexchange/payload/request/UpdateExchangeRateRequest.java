package com.alfredo.currencyexchange.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateExchangeRateRequest {
    private Integer id;
    @NotNull(message = "The value to change is required")
    private Double value;
}
