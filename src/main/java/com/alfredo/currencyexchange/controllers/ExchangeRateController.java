package com.alfredo.currencyexchange.controllers;

import com.alfredo.currencyexchange.payload.request.ConvertCurrencyRequest;
import com.alfredo.currencyexchange.payload.request.CreateExchangeRateRequest;
import com.alfredo.currencyexchange.payload.request.UpdateExchangeRateRequest;
import com.alfredo.currencyexchange.payload.response.common.ActionResult;
import com.alfredo.currencyexchange.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public Single<ResponseEntity<Object>> createExchangeRate(@Valid @RequestBody CreateExchangeRateRequest request) {
        return service.createExchangeRate(request)
                .subscribeOn(Schedulers.io())
                .map(response -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode())));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Single<ResponseEntity<Object>> updateExchangeRate(@PathVariable(value = "id") Integer exchangeRateId,
                                                             @Valid @RequestBody UpdateExchangeRateRequest request) {
        request.setId(exchangeRateId);
        ActionResult<Object> result = new ActionResult<>();
        result.setMessage("Exchange rate successfully updated");
        return service.updateExchangeRate(request)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(result));
    }

    @GetMapping("/value")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Single<ResponseEntity<Object>> getExchangeRateValue(@RequestParam(defaultValue = "0") Integer sourceCurrencyId,
                                                               @RequestParam(defaultValue = "0") Integer destinationCurrencyId) {
        return service.getExchangeRateValue(sourceCurrencyId, destinationCurrencyId)
                .subscribeOn(Schedulers.io())
                .map(response -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode())));
    }

    @PostMapping("/convert")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Single<ResponseEntity<Object>> convertCurrency(@Valid @RequestBody ConvertCurrencyRequest request) {
        return service.convertCurrency(request)
                .subscribeOn(Schedulers.io())
                .map(response -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode())));
    }
}
