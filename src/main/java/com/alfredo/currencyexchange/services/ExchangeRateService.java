package com.alfredo.currencyexchange.services;

import com.alfredo.currencyexchange.payload.request.ConvertCurrencyRequest;
import com.alfredo.currencyexchange.payload.request.CreateExchangeRateRequest;
import com.alfredo.currencyexchange.payload.request.UpdateExchangeRateRequest;
import com.alfredo.currencyexchange.payload.response.common.ActionResult;
import rx.Completable;
import rx.Single;

public interface ExchangeRateService {
    Single<ActionResult<Object>> createExchangeRate(CreateExchangeRateRequest request);
    Completable updateExchangeRate(UpdateExchangeRateRequest request);
    Single<ActionResult<Object>> getExchangeRateValue(Integer sourceCurrencyId, Integer destinationCurrencyId);
    Single<ActionResult<Object>> convertCurrency(ConvertCurrencyRequest request);
}
