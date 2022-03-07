package com.alfredo.currencyexchange.repository;

import com.alfredo.currencyexchange.models.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Integer> {
}
