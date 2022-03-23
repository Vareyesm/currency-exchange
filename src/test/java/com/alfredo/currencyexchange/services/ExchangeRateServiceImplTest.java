package com.alfredo.currencyexchange.services;

import com.alfredo.currencyexchange.models.ExchangeRate;
import com.alfredo.currencyexchange.payload.response.common.ActionResult;
import com.alfredo.currencyexchange.repository.CurrencyRepository;
import com.alfredo.currencyexchange.repository.ExchangeHistoryRepository;
import com.alfredo.currencyexchange.repository.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Single;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ExchangeRateServiceImplTest {
    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ExchangeHistoryRepository exchangeHistoryRepository;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getExchangeRateValueOK() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setValue(3.14);
        Optional<ExchangeRate> optionalExchangeRate = Optional.of(exchangeRate);
        when(exchangeRateRepository.findBySourceCurrencyIdAndDestinationCurrencyId(anyInt(), anyInt())).thenReturn(optionalExchangeRate);
        Single<ActionResult<Object>> response = exchangeRateService.getExchangeRateValue(anyInt(),anyInt());
        assertNotNull(response);
    }

    @Test
    void convertCurrency() {
    }
}