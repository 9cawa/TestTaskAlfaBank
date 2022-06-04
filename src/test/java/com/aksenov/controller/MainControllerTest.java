package com.aksenov.controller;

import com.aksenov.domain.ExchangeStatus;
import com.aksenov.exception.NoSuchCurrencyException;
import com.aksenov.service.exchange.ExchangeServiceImpl;
import com.aksenov.service.giphy.GiphyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
    private static final String VALID_CURRENCY = "RUB";
    private static final String VALID_CURRENCY_PATH = String.format("/%s/show-gif", VALID_CURRENCY);
    private static final ExchangeStatus EXCHANGE_STATUS = ExchangeStatus.DECREASED;
    private static final String GIF_PAGE = "<img src=\"https://media0.giphy.com/media/PsJ3zE5d9b0xgNCZuy/giphy.gif\">";
    private static final String INVALID_CURRENCY = "ZXC";
    private static final String INVALID_CURRENCY_PATH = String.format("/%s/show-gif", INVALID_CURRENCY);
    private static final String BAD_REQUEST = "There is no such currency";

    @MockBean
    private ExchangeServiceImpl exchangeService;

    @MockBean
    private GiphyServiceImpl giphyService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() throws NoSuchCurrencyException {
        when(exchangeService.getExchangeStatusByCurrency(VALID_CURRENCY)).thenReturn(EXCHANGE_STATUS);
        when(giphyService.showGifPageByExchangeStatus(EXCHANGE_STATUS)).thenReturn(GIF_PAGE);

        when(exchangeService.getExchangeStatusByCurrency(INVALID_CURRENCY)).thenThrow(NoSuchCurrencyException.class);
    }

    @Test
    public void showGifShouldReturnGifPage() throws Exception {
        this.mockMvc.perform(get(VALID_CURRENCY_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<img src=\"")));
    }

    @Test
    public void showGifShouldHaveBadRequestStatus() throws Exception {
        this.mockMvc.perform(get(INVALID_CURRENCY_PATH))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(BAD_REQUEST));
    }
}
