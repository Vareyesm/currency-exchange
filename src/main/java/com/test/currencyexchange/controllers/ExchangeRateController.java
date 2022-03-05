package com.test.currencyexchange.controllers;

import com.test.currencyexchange.payload.request.CreateExchangeRateRequest;
import com.test.currencyexchange.payload.request.UpdateExchangeRateRequest;
import com.test.currencyexchange.payload.response.common.ActionResult;
import com.test.currencyexchange.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exchanges-rates")
public class ExchangeRateController {
    @Autowired
    ExchangeRateService service;

    @PostMapping
    public Single<ResponseEntity<Object>> createExchangeRate(@Valid @RequestBody CreateExchangeRateRequest request) {
        return service.createExchangeRate(request)
                .subscribeOn(Schedulers.io())
                .map(response -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode())));
    }

    @PutMapping(value = "/{id}")
    public Single<ResponseEntity<Object>> updateExchangeRate(@PathVariable(value = "id") Integer exchangeRateId,
                                                             @RequestBody UpdateExchangeRateRequest request) {
        request.setId(exchangeRateId);
        ActionResult<Object> result = new ActionResult<>();
        result.setMessage("Exchange rate successfully updated");
        return service.updateExchangeRate(request)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(result));
    }
}
