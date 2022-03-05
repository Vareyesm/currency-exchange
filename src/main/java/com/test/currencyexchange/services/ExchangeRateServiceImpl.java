package com.test.currencyexchange.services;

import com.test.currencyexchange.models.mappers.ExchangeRateMapper;
import com.test.currencyexchange.payload.request.ExchangeRateRequest;
import com.test.currencyexchange.payload.response.common.ActionResult;
import com.test.currencyexchange.repository.CurrencyRepository;
import com.test.currencyexchange.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rx.Completable;
import rx.Single;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Single<ActionResult<Object>> createExchangeRate(ExchangeRateRequest request) {
        ActionResult<Object> result = this.validateCreateExchangeRate(request);
        return Single.create(singleSubscriber -> {
            if (result.getErrors() == null) {
                exchangeRateRepository.save(ExchangeRateMapper.toCreate(request));
                result.setStatusCode(HttpStatus.CREATED.value());
                result.setMessage("Exchange rate successfully saved");
            }
            singleSubscriber.onSuccess(result);
        });
    }

    private ActionResult<Object> validateCreateExchangeRate(ExchangeRateRequest request) {
        ActionResult<Object> result = new ActionResult<>();
        Map<String, String> errors = new HashMap<>();

        boolean existsSourceCurrency = currencyRepository.existsById(request.getSourceCurrencyId());
        boolean existsDestinationCurrency = currencyRepository.existsById(request.getDestinationCurrencyId());
        boolean existsExchangeRate = exchangeRateRepository.existsBySourceCurrencyIdAndDestinationCurrencyId(request.getSourceCurrencyId(), request.getDestinationCurrencyId());

        if (!existsSourceCurrency || !existsDestinationCurrency || existsExchangeRate) {
            result.setStatusCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("An error occurred while saving the exchange rate");
            result.setErrors(errors);

            if (existsExchangeRate)
                errors.put("global", "There is already a registered exchange rate");
            else
                errors.put("global", "Source or destination currency does not exist");
        }

        return result;
    }

    @Override
    public Completable updateExchangeRate(ExchangeRateRequest request) {
        return null;
    }

    @Override
    public Single<Double> getExchangeRateValue() {
        return null;
    }
}
