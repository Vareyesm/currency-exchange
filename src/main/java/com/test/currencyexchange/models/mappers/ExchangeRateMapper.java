package com.test.currencyexchange.models.mappers;

import com.test.currencyexchange.models.Currency;
import com.test.currencyexchange.models.ExchangeHistory;
import com.test.currencyexchange.models.ExchangeRate;
import com.test.currencyexchange.payload.request.ConvertCurrencyRequest;
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

    public static ExchangeHistory toHistory(ConvertCurrencyRequest request, Double outputValue, String username) {
        ExchangeHistory exchangeHistory = new ExchangeHistory();
        exchangeHistory.setSourceCurrency(Currency.builder()
                .id(request.getSourceCurrencyId())
                .build());
        exchangeHistory.setDestinationCurrency(Currency.builder()
                .id(request.getDestinationCurrencyId())
                .build());
        exchangeHistory.setUsername(username);
        exchangeHistory.setExchangeRateValue(request.getExchangeRateValue());
        exchangeHistory.setInputValue(request.getAmountConvert());
        exchangeHistory.setOutputValue(outputValue);
        exchangeHistory.setCreationDate(new Date());
        return exchangeHistory;
    }
}
