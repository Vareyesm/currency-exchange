package com.test.currencyexchange.services;

import com.test.currencyexchange.payload.request.ExchangeRateRequest;
import com.test.currencyexchange.payload.response.common.ActionResult;
import rx.Completable;
import rx.Single;

public interface ExchangeRateService {
    Single<ActionResult<Object>> createExchangeRate(ExchangeRateRequest request);
    Completable updateExchangeRate(ExchangeRateRequest request);
    Single<Double> getExchangeRateValue();
}
