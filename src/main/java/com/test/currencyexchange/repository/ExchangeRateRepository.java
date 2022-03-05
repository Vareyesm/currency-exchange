package com.test.currencyexchange.repository;

import com.test.currencyexchange.models.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {
    Optional<ExchangeRate> findBySourceCurrencyAndDestinationCurrency(Integer sourceCurrencyId, Integer destinationCurrencyId);
    boolean existsBySourceCurrencyIdAndDestinationCurrencyId(Integer sourceCurrencyId, Integer destinationCurrencyId);
}
