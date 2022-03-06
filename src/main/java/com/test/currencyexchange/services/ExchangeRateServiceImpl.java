package com.test.currencyexchange.services;

import com.test.currencyexchange.exceptions.CustomException;
import com.test.currencyexchange.models.ExchangeRate;
import com.test.currencyexchange.models.mappers.ExchangeRateMapper;
import com.test.currencyexchange.payload.request.ConvertCurrencyRequest;
import com.test.currencyexchange.payload.request.CreateExchangeRateRequest;
import com.test.currencyexchange.payload.request.UpdateExchangeRateRequest;
import com.test.currencyexchange.payload.response.common.ActionResult;
import com.test.currencyexchange.repository.CurrencyRepository;
import com.test.currencyexchange.repository.ExchangeHistoryRepository;
import com.test.currencyexchange.repository.ExchangeRateRepository;
import com.test.currencyexchange.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ExchangeHistoryRepository exchangeHistoryRepository;

    @Override
    public Single<ActionResult<Object>> createExchangeRate(CreateExchangeRateRequest request) {
        return Single.create(singleSubscriber -> {
            ActionResult<Object> result = validateCreateExchangeRate(request);
            if (result.getErrors().isEmpty()) {
                exchangeRateRepository.save(ExchangeRateMapper.toCreate(request));
                result.setStatusCode(HttpStatus.CREATED.value());
                result.setMessage("Exchange rate successfully saved");
                singleSubscriber.onSuccess(result);
            } else
                singleSubscriber.onError(new CustomException(result));

        });
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

    @Override
    public Single<ActionResult<Object>> convertCurrency(ConvertCurrencyRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl)auth.getPrincipal();
        return Single.create(singleSubscriber -> {
            ActionResult<Object> actionResult = validateIfSourceAndDestinationsCurrencyExists(request.getSourceCurrencyId(), request.getDestinationCurrencyId());
            if (actionResult.getErrors().isEmpty()) {
                Double outputValue = request.getAmountConvert() / request.getExchangeRateValue();
                exchangeHistoryRepository.save(ExchangeRateMapper.toHistory(request, outputValue, userDetails.getUsername()));
                actionResult.setStatusCode(HttpStatus.OK.value());
                actionResult.setMessage("Successful conversion");
                actionResult.setResult(outputValue);
                singleSubscriber.onSuccess(actionResult);
            } else
                singleSubscriber.onError(new CustomException(actionResult));
        });
    }

    private ActionResult<Object> validateCreateExchangeRate(CreateExchangeRateRequest request) {
        ActionResult<Object> actionResult = validateIfSourceAndDestinationsCurrencyExists(request.getSourceCurrencyId(), request.getDestinationCurrencyId());
        Map<String, String> errors = actionResult.getErrors();
        boolean existsExchangeRate = exchangeRateRepository.existsBySourceCurrencyIdAndDestinationCurrencyId(request.getSourceCurrencyId(), request.getDestinationCurrencyId());

        if (existsExchangeRate) {
            actionResult.setStatusCode(HttpStatus.BAD_REQUEST.value());
            actionResult.setMessage("An error occurred while saving the exchange rate");
            errors.put("global", "There is already a registered exchange rate");
            actionResult.setErrors(errors);
        }
        return actionResult;
    }

    private ActionResult<Object> validateIfSourceAndDestinationsCurrencyExists(Integer sourceCurrencyId, Integer destinationCurrencyId) {
        ActionResult<Object> actionResult = new ActionResult<>();
        Map<String, String> errors = new HashMap<>();

        boolean existsSourceCurrency = currencyRepository.existsById(sourceCurrencyId);
        boolean existsDestinationCurrency = currencyRepository.existsById(destinationCurrencyId);

        if (!existsSourceCurrency || !existsDestinationCurrency) {
            actionResult.setStatusCode(HttpStatus.BAD_REQUEST.value());
            actionResult.setMessage("Source or destination currency does not exist");

            if (!existsSourceCurrency)
                errors.put("sourceCurrencyId", "Source currency does not exist");
            if (!existsDestinationCurrency)
                errors.put("destinationCurrencyId", "Destination currency does not exist");

            actionResult.setErrors(errors);
        }
        return actionResult;
    }

    private ActionResult<Object> validateGetExchangeRateValue(Integer sourceCurrencyId, Integer destinationCurrencyId) {
        ActionResult<Object> actionResult = new ActionResult<>();
        Map<String, String> errors = new HashMap<>();
        actionResult.setStatusCode(HttpStatus.BAD_REQUEST.value());
        actionResult.setMessage("Invalid data");
        if (sourceCurrencyId == 0)
            errors.put("sourceCurrencyId", "The source currency is required");
        if (destinationCurrencyId == 0)
            errors.put("destinationCurrencyId", "The destination currency is required");
        actionResult.setErrors(errors);
        return actionResult;
    }
}
