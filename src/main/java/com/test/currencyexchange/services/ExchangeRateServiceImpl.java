package com.test.currencyexchange.services;

import com.test.currencyexchange.exceptions.CustomException;
import com.test.currencyexchange.models.ExchangeRate;
import com.test.currencyexchange.models.mappers.ExchangeRateMapper;
import com.test.currencyexchange.payload.request.CreateExchangeRateRequest;
import com.test.currencyexchange.payload.request.UpdateExchangeRateRequest;
import com.test.currencyexchange.payload.response.common.ActionResult;
import com.test.currencyexchange.repository.CurrencyRepository;
import com.test.currencyexchange.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rx.Completable;
import rx.Single;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Single<ActionResult<Object>> createExchangeRate(CreateExchangeRateRequest request) {
        ActionResult<Object> result = this.validateCreateExchangeRate(request);
        return Single.create(singleSubscriber -> {
            if (result.getErrors() == null) {
                exchangeRateRepository.save(ExchangeRateMapper.toCreate(request));
                result.setStatusCode(HttpStatus.CREATED.value());
                result.setMessage("Exchange rate successfully saved");
                singleSubscriber.onSuccess(result);
            } else
                singleSubscriber.onError(new CustomException(result));

        });
    }

    private ActionResult<Object> validateCreateExchangeRate(CreateExchangeRateRequest request) {
        ActionResult<Object> result = new ActionResult<>();
        Map<String, String> errors = new HashMap<>();

        boolean existsSourceCurrency = currencyRepository.existsById(request.getSourceCurrencyId());
        boolean existsDestinationCurrency = currencyRepository.existsById(request.getDestinationCurrencyId());
        boolean existsExchangeRate = exchangeRateRepository.existsBySourceCurrencyIdAndDestinationCurrencyId(request.getSourceCurrencyId(), request.getDestinationCurrencyId());

        if (!existsSourceCurrency || !existsDestinationCurrency || existsExchangeRate) {
            result.setStatusCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("An error occurred while saving the exchange rate");

            if (existsExchangeRate)
                errors.put("global", "There is already a registered exchange rate");
            else
                errors.put("global", "Source or destination currency does not exist");

            result.setErrors(errors);
        }

        return result;
    }

    @Override
    public Completable updateExchangeRate(UpdateExchangeRateRequest request) {
        return Completable.create(completableSubscriber -> {
            Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findById(request.getId());
            ActionResult<Object> result = new ActionResult<>();
            Map<String, String> errors = new HashMap<>();

            if (optionalExchangeRate.isPresent()) {
                ExchangeRate exchangeRate = optionalExchangeRate.get();
                exchangeRate.setValue(request.getValue());
                exchangeRate.setUpdateDate(new Date());
                exchangeRateRepository.save(exchangeRate);
                completableSubscriber.onCompleted();
            } else {
                result.setStatusCode(HttpStatus.BAD_REQUEST.value());
                result.setMessage("An error occurred while updating the exchange rate");
                errors.put("global", "Exchange rate to update not found");
                result.setErrors(errors);
                completableSubscriber.onError(new CustomException(result));
            }
        });
    }

    @Override
    public Single<ActionResult<Object>> getExchangeRateValue(Integer sourceCurrencyId, Integer destinationCurrencyId) {
        return Single.create(singleSubscriber -> {
            ActionResult<Object> actionResult = validateGetExchangeRateValue(sourceCurrencyId, destinationCurrencyId);
            if (actionResult.getErrors().isEmpty()) {
                Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findBySourceCurrencyIdAndDestinationCurrencyId(sourceCurrencyId, destinationCurrencyId);
                actionResult.setStatusCode(HttpStatus.OK.value());

                if (exchangeRate.isPresent()) {
                    actionResult.setMessage("Exchange rate value found");
                    actionResult.setResult(exchangeRate.get().getValue());
                    singleSubscriber.onSuccess(actionResult);
                } else {
                    actionResult.setMessage("Exchange rate value not found");
                    singleSubscriber.onSuccess(actionResult);
                }
            } else
                singleSubscriber.onError(new CustomException(actionResult));
        });
    }

    private ActionResult<Object> validateGetExchangeRateValue(Integer sourceCurrencyId, Integer destinationCurrencyId) {
        ActionResult<Object> actionResult = new ActionResult<>();
        Map<String, String> errors = new HashMap<>();
        actionResult.setStatusCode(HttpStatus.BAD_REQUEST.value());
        actionResult.setMessage("Invalid data");
        if (sourceCurrencyId == 0 )
            errors.put("sourceCurrencyId", "The source currency is required");
        if (destinationCurrencyId == 0 )
            errors.put("destinationCurrencyId", "The destination currency is required");
        actionResult.setErrors(errors);
        return actionResult;
    }
}
