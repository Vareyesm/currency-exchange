package com.test.currencyexchange.services;

import com.test.currencyexchange.payload.request.CreateExchangeRateRequest;
import com.test.currencyexchange.payload.request.UpdateExchangeRateRequest;
import com.test.currencyexchange.payload.response.common.ActionResult;
import rx.Completable;
import rx.Single;

public interface ExchangeRateService {
    Single<ActionResult<Object>> createExchangeRate(CreateExchangeRateRequest request);
    Completable updateExchangeRate(UpdateExchangeRateRequest request);
    Single<ActionResult<Object>> getExchangeRateValue(Integer sourceCurrencyId, Integer destinationCurrencyId);
}
