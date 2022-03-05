package com.test.currencyexchange.models.mappers;

import com.test.currencyexchange.models.Currency;
import com.test.currencyexchange.models.ExchangeRate;
import com.test.currencyexchange.payload.request.CreateExchangeRateRequest;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ExchangeRateMapper {
    public static ExchangeRate toCreate(CreateExchangeRateRequest request) {
        ExchangeRate exchangeRate = new ExchangeRate();
        BeanUtils.copyProperties(request, exchangeRate);
        exchangeRate.setSourceCurrency(Currency.builder()
                .id(request.getSourceCurrencyId())
                .build());
        exchangeRate.setDestinationCurrency(Currency.builder()
                .id(request.getDestinationCurrencyId())
                .build());
        exchangeRate.setCreationDate(new Date());
        return exchangeRate;
    }
}
