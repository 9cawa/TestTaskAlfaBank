package com.aksenov.service;

import com.aksenov.client.OpenExchangeRatesClient;
import com.aksenov.domain.Exchange;
import com.aksenov.domain.ExchangeStatus;
import com.aksenov.exception.NoSuchCurrencyException;
import com.aksenov.service.exchange.ExchangeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExchangeServiceImplTest {

    private static final Exchange EXCHANGE_INCREASED =
            new Exchange("d1","l1","USD",new HashMap<>());
    private static final Exchange EXCHANGE_DECREASED =
            new Exchange("d2","l2","USD", new HashMap<>());
    static {
        EXCHANGE_INCREASED.getRates().put("RUB", 100.0);
        EXCHANGE_DECREASED.getRates().put("RUB", 50.0);
    }
    private static final String VALID_CURRENCY = "RUB";
    private static final String INVALID_CURRENCY = "ZXC";
    private static final LocalDate TODAY = LocalDateTime.now(ZoneOffset.UTC).toLocalDate();
    private static final LocalDate YESTERDAY = LocalDateTime.now(ZoneOffset.UTC).minusDays(1).toLocalDate();

    @MockBean
    private OpenExchangeRatesClient exchangeRatesClient;

    @Autowired
    private ExchangeServiceImpl exchangeService;

    @BeforeEach
    void init() {
        when(exchangeRatesClient.getHistoricalExchange(anyString(), eq(TODAY.toString()), anyString()))
                .thenReturn(EXCHANGE_INCREASED);
        when(exchangeRatesClient.getHistoricalExchange(anyString(), eq(YESTERDAY.toString()), anyString()))
                .thenReturn(EXCHANGE_DECREASED);
    }

    @Test
    public void getExchangeByDateShouldReturnIncreased() {
        Exchange exchange = exchangeService.getExchangeByDate(TODAY);
        assertThat(exchange).isEqualTo(EXCHANGE_INCREASED);
    }

    @Test
    public void getExchangeByDateShouldReturnDecreased() {
        Exchange exchange = exchangeService.getExchangeByDate(YESTERDAY);
        assertThat(exchange).isEqualTo(EXCHANGE_DECREASED);
    }

    @Test
    public void getExchangeStatusByCurrencyShouldReturnIncreased() throws NoSuchCurrencyException {
        ExchangeStatus status = exchangeService.getExchangeStatusByCurrency(VALID_CURRENCY);
        assertThat(status).isEqualTo(ExchangeStatus.INCREASED);
    }

    @Test()
    public void getExchangeStatusByCurrencyShouldThrowException() {
        assertThatThrownBy(() -> exchangeService.getExchangeStatusByCurrency(INVALID_CURRENCY))
                .isInstanceOf(NoSuchCurrencyException.class);
    }
}
