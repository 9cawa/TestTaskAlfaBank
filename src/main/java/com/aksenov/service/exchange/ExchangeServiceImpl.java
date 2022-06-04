package com.aksenov.service.exchange;

import com.aksenov.client.OpenExchangeRatesClient;
import com.aksenov.domain.Exchange;
import com.aksenov.domain.ExchangeStatus;
import com.aksenov.exception.NoSuchCurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class ExchangeServiceImpl  implements ExchangeService {
    @Autowired
    private OpenExchangeRatesClient exchangeRatesClient;

    @Value("${OpenExchange.app-id}")
    private String exchangeAppId;

    @Value("${OpenExchange.current-currency}")
    private String currentCurrency;

    @Override
    public Exchange getExchangeByDate(LocalDate date) {
        Exchange exchange = exchangeRatesClient.getHistoricalExchange(exchangeAppId, date.toString(), currentCurrency);
        return exchange;
    }

    @Override
    public ExchangeStatus getExchangeStatusByCurrency(String currency) throws NoSuchCurrencyException {
        Exchange todayExchange = getExchangeByDate(LocalDateTime.now(ZoneOffset.UTC).toLocalDate());
        Exchange yesterdayExchange = getExchangeByDate(LocalDateTime.now(ZoneOffset.UTC).minusDays(1).toLocalDate());

        if (todayExchange.getRates().get(currency) == null || yesterdayExchange.getRates().get(currency) == null)
            throw new NoSuchCurrencyException();

        if (todayExchange.getRates().get(currency) > yesterdayExchange.getRates().get(currency))
            return ExchangeStatus.INCREASED;
        else
            return ExchangeStatus.DECREASED;
    }
}
