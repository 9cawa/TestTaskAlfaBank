package com.aksenov.service.exchange;

import com.aksenov.domain.Exchange;
import com.aksenov.domain.ExchangeStatus;
import com.aksenov.exception.NoSuchCurrencyException;

import java.time.LocalDate;

public interface ExchangeService {

    Exchange getExchangeByDate(LocalDate date);

    ExchangeStatus getExchangeStatusByCurrency(String currency) throws NoSuchCurrencyException;
}
