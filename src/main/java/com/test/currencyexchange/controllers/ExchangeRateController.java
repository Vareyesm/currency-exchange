package com.test.currencyexchange.controllers;

import com.test.currencyexchange.payload.request.ExchangeRateRequest;
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
    public Single<ResponseEntity<Object>> addExchange(@Valid @RequestBody ExchangeRateRequest request) {
        return service.createExchangeRate(request)
                .subscribeOn(Schedulers.io())
                .map(response -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode())));
    }
}
